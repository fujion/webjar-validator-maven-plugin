package org.fujion.webjar.validator;

import java.io.File;
import java.util.List;

/**
 * Validates that a file does not exist.
 */
public class FileNotExistsRule extends FileExistsRule {

    public FileNotExistsRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    protected boolean passes() {
        return !super.passes();
    }
}
