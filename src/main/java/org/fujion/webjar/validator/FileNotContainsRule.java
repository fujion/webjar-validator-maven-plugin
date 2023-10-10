package org.fujion.webjar.validator;

import java.io.File;
import java.util.List;

/**
 * Validates that a file does not contain a specified string.
 */
public class FileNotContainsRule extends FileContainsRule {

    public FileNotContainsRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    protected boolean passes() {
        return !super.passes();
    }
}
