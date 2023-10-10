package org.fujion.webjar.validator;

import org.apache.commons.io.IOUtils;
import org.fujion.common.MiscUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Validates that a file contains a specified string.
 */
public class FileContainsRule extends AbstractRule {

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
            String ln;

            while ((ln = contents.readLine()) != null) {
                if (ln.contains(stringToMatch)) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
}
