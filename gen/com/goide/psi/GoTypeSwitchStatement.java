// This is a generated file. Not intended for manual editing.
package com.goide.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GoTypeSwitchStatement extends GoStatement {

  @Nullable
  GoSimpleStatement getSimpleStatement();

  @NotNull
  List<GoTypeCaseClause> getTypeCaseClauseList();

  @Nullable
  GoTypeSwitchGuard getTypeSwitchGuard();

  @NotNull
  PsiElement getSwitch();

}
