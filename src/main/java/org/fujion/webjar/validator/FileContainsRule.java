package org.fujion.webjar.validator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.fujion.common.MiscUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validates that a file contains a specified string.
 */
public class FileContainsRule extends AbstractRule {

    private static final String REGEX_DELIMITER = "/";

    public FileContainsRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    protected boolean passes() {
        try (BufferedReader contents = IOUtils.toBufferedReader(new FileReader(file))) {
            String stringToMatch = args.get(0);
            Pattern regex = null;

            if (stringToMatch.startsWith(REGEX_DELIMITER) && stringToMatch.endsWith(REGEX_DELIMITER)) {
                stringToMatch = StringUtils.removeStart(stringToMatch, REGEX_DELIMITER);
                stringToMatch = StringUtils.removeEnd(stringToMatch, REGEX_DELIMITER);
                regex = Pattern.compile(stringToMatch);
            }

            String ln;

            while ((ln = contents.readLine()) != null) {
                boolean found = regex != null ? regex.matcher(ln).matches() : ln.contains(stringToMatch);

                if (found) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
}
