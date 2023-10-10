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
    private MavenProject project;

    @Parameter(property = "rules", readonly = true, required = true)
    private List<String> rules;

    /**
     * Parses the list of rules and executes each one.
     *
     * @throws MojoExecutionException If any of the rules fail.
     */
    @Override
    public void execute() throws MojoExecutionException {
        if (rules.isEmpty()) {
            getLog().warn("No validation rules were specified.  Exiting...");
            return;
        }

        RuleFactory factory = new RuleFactory(project);

        boolean failed = false;

        for (String ruleStr : rules) {
            AbstractRule rule = factory.createRule(ruleStr);
            boolean passes = rule.passes();
            getLog().info(String.format("Rule %s: %s", passes ? "succeeded" : "failed   ", ruleStr));
            failed |= !passes;
        }

        if (failed) {
            throw new MojoExecutionException("Validation failed.");
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\n" + String.join("\n   ", rules);
    }
}
