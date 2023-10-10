package org.fujion.webjar.validator;

import java.io.File;
import java.util.List;

/**
 * Validates that a file exists.
 */
public class FileExistsRule extends AbstractRule {

    public FileExistsRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    protected boolean passes() {
        return file.exists() && file.isFile();
    }
}
