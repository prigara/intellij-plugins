package com.intellij.lang.javascript.flex.projectStructure.model.impl;

import com.intellij.lang.javascript.flex.projectStructure.FlexProjectLevelCompilerOptionsHolder;
import com.intellij.lang.javascript.flex.projectStructure.model.ModuleOrProjectCompilerOptions;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.annotations.Property;
import org.jetbrains.annotations.NotNull;

@State(
  name = "FlexIdeProjectLevelCompilerOptionsHolder",
  storages = {
    @Storage("flexCompiler.xml"),
    @Storage(value = StoragePathMacros.WORKSPACE_FILE, deprecated = true)
  }
)
public class FlexProjectLevelCompilerOptionsHolderImpl extends FlexProjectLevelCompilerOptionsHolder
  implements PersistentStateComponent<FlexProjectLevelCompilerOptionsHolderImpl.State> {

  private final CompilerOptionsImpl myModel;
  private final Project myProject;

  public FlexProjectLevelCompilerOptionsHolderImpl(final Project project) {
    myProject = project;
    myModel = new CompilerOptionsImpl(project, true);
  }

  public FlexProjectLevelCompilerOptionsHolderImpl.State getState() {
    FlexProjectLevelCompilerOptionsHolderImpl.State state = new State();
    state.compilerOptions = myModel.getState(myProject);
    return state;
  }

  @Override
  public ModuleOrProjectCompilerOptions getProjectLevelCompilerOptions() {
    return myModel;
  }

  public void loadState(@NotNull final FlexProjectLevelCompilerOptionsHolderImpl.State state) {
    myModel.loadState(state.compilerOptions);
  }

  public static class State {
    @Property(surroundWithTag = false)
    public CompilerOptionsImpl.State compilerOptions = new CompilerOptionsImpl.State();
  }
}
