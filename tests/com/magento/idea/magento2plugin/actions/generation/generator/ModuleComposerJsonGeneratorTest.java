/*
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */
package com.magento.idea.magento2plugin.actions.generation.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.magento.idea.magento2plugin.actions.generation.data.ModuleComposerJsonData;
import com.magento.idea.magento2plugin.magento.files.ComposerJson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleComposerJsonGeneratorTest extends BaseGeneratorTestCase {

    public void testGenerateModuleFile() {
        String filePath = this.getFixturePath(ComposerJson.FILE_NAME);
        PsiFile expectedFile = myFixture.configureByFile(filePath);
        PsiDirectory projectDir = getProjectDirectory();

        PsiFile composerJson = generateComposerJson(true, projectDir);

        assertGeneratedFileIsCorrect(
            expectedFile,
            projectDir.getVirtualFile().getPath() + "/Test/Module",
            composerJson
        );
    }

    public void testGenerateFileInRoot() {
        String filePath = this.getFixturePath(ComposerJson.FILE_NAME);
        PsiFile expectedFile = myFixture.configureByFile(filePath);
        PsiDirectory projectDir = getProjectDirectory();

        PsiFile composerJson = generateComposerJson(false, projectDir);

        assertGeneratedFileIsCorrect(
            expectedFile,
            projectDir.getVirtualFile().getPath(),
            composerJson
        );
    }

    private PsiFile generateComposerJson(boolean createModuleDirectories, PsiDirectory projectDir) {
        Project project = myFixture.getProject();
        List<String> dependencies = new ArrayList<>(Arrays.asList("Foo_Bar", "Magento_Backend"));
        List<String> licenses = new ArrayList<>(Arrays.asList("Test License 1", "Test License 2"));
        ModuleComposerJsonData composerJsonData = new ModuleComposerJsonData(
            "Test",
            "Module",
            projectDir,
            "test-description",
            "test/module",
            "1.0.0-dev",
            licenses,
            dependencies,
            createModuleDirectories
        );
        ModuleComposerJsonGenerator composerJsonGenerator = new ModuleComposerJsonGenerator(composerJsonData, project);
        return composerJsonGenerator.generate("test");
    }
}
