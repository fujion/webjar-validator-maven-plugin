package org.fujion.webjar.validator;

import java.io.File;
import java.util.List;

/**
 * Validates that a directory exists.
 */
public class DirExistsRule extends AbstractRule {

    public DirExistsRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    protected boolean passes() {
        return file.exists() && file.isDirectory();
    }
}
