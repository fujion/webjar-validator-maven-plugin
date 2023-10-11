package org.fujion.webjar.validator;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.Validate;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Factory for generating rules from raw rule strings.
 */
public class RuleFactory {

    private static final Pattern argumentSplitter = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");

    private static final String TARGET_FILENAME = "%s/META-INF/resources/webjars/%s/%s/%s";

    private final MavenProject project;

    public RuleFactory(MavenProject project) {
        this.project = project;
    }

    /**
     * Creates a rule from a rule string.
     *
     * @param ruleStr The rule as a string.
     * @return The corresponding rule.
     */
    public AbstractRule createRule(String ruleStr) {
        List<String> args = parse(ruleStr);
        String ruleTypeStr = args.remove(0);
        RuleType ruleType = EnumUtils.getEnumIgnoreCase(RuleType.class, ruleTypeStr);
        Validate.isTrue(ruleType != null, "Did not recognize the rule name: %s", ruleTypeStr);

        String filePath = String.format(TARGET_FILENAME,
                project.getBuild().getOutputDirectory(),
                project.getArtifact().getArtifactId(),
                project.getArtifact().getVersion(),
                args.remove(0));
        File file = new File(filePath);

        return switch (ruleType) {
            case DIR_EXISTS -> new DirExistsRule(file, args);
            case DIR_NOT_EXISTS -> new DirNotExistsRule(file, args);
            case FILE_EXISTS -> new FileExistsRule(file, args);
            case FILE_NOT_EXISTS -> new FileNotExistsRule(file, args);
            case FILE_CONTAINS -> new FileContainsRule(file, args);
            case FILE_NOT_CONTAINS -> new FileNotContainsRule(file, args);
        };
    }

    /**
     * Splits a rule string into individual components. Handles quoted arguments.
     * <p>
     * Expected format is: &lt;rule&gt; &lt;file path&gt; &lt;...arguments&gt;
     *
     * @param ruleStr The rule string.
     * @return The rule's components.
     */
    private List<String> parse(String ruleStr) {
        List<String> args = new ArrayList<>();
        Matcher matcher = argumentSplitter.matcher(ruleStr);

        while (matcher.find()) {
            String pc = matcher.group(1).replace("\"", "").trim();

            if (!pc.isBlank()) {
                args.add(pc);
            }
        }

        Validate.isTrue(args.size() >= 2, "Invalid number of arguments.");
        return args;
    }


}
