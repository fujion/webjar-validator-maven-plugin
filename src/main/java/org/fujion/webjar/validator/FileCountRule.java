package org.fujion.webjar.validator;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.util.List;

/**
 * Validates that a directory contains a specified number of matching files.
 */
public class FileCountRule extends AbstractRule {

    public FileCountRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    protected boolean passes() {
        String wildcard;
        File directory;
        int count = Integer.parseInt(args.get(0));

        if (file.isDirectory()) {
            directory = file;
            wildcard = "*.*";
        } else {
            Path path = Path.of(file.getAbsolutePath());
            wildcard = path.getFileName().toString();
            directory = path.getParent().toFile();
        }

        FileFilter filter = WildcardFileFilter.builder().setWildcards(wildcard).get();
        File[] matches = directory.listFiles(filter);
        return matches != null && matches.length == count;
    }
}
