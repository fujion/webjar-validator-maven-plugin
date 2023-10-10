package org.fujion.webjar.validator;

import java.io.File;
import java.util.List;

/**
 * Validates that a directory does not exist.
 */
public class DirNotExistsRule extends DirExistsRule {

    public DirNotExistsRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    protected boolean passes() {
        return !super.passes();
    }
}
