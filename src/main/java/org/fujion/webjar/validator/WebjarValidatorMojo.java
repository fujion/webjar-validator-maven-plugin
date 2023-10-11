package org.fujion.webjar.validator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;

@Mojo(name = "validate-webjar", defaultPhase = LifecyclePhase.PACKAGE)
public class WebjarValidatorMojo extends AbstractMojo {

    @Parameter(property = "project", readonly = true, required = true)
    @SuppressWarnings("all")
    private MavenProject project;

    @Parameter(property = "rules", required = true)
    @SuppressWarnings("all")
    private List<String> rules;

    @Parameter(property = "skip")
    @SuppressWarnings("all")
    private boolean skip;

    /**
     * Parses the list of rules and executes each one.
     *
     * @throws MojoExecutionException If any of the rules fail.
     */
    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skip property was set to true.  Exiting...");
        }

        if (rules.isEmpty()) {
            getLog().warn("No validation rules were specified.  Exiting...");
            return;
        }

        RuleFactory factory = new RuleFactory(project);

        boolean failed = false;

        for (String ruleStr : rules) {
            try {
                AbstractRule rule = factory.createRule(ruleStr);
                boolean passes = rule.passes();
                logResult(ruleStr, passes ? "succeeded" : "failed");
                failed |= !passes;
            } catch (Exception e) {
                logResult(ruleStr, "error");
                getLog().error("  The error was: " + e.getMessage());
                failed = true;
            }
        }

        if (failed) {
            throw new MojoExecutionException("Validation failed.");
        }
    }

    private void logResult(String ruleStr, String result) {
        getLog().info(String.format("Rule %-9s: %s", result, ruleStr));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\n" + String.join("\n   ", rules);
    }
}
