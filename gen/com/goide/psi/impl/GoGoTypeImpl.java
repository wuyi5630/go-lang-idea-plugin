// This is a generated file. Not intended for manual editing.
package com.goide.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.goide.GoTypes.*;
import com.goide.psi.*;

public class GoGoTypeImpl extends GoCompositeElementImpl implements GoGoType {

  public GoGoTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GoVisitor) ((GoVisitor)visitor).visitGoType(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public GoTypeLit getTypeLit() {
    return findChildByClass(GoTypeLit.class);
  }

  @Override
  @Nullable
  public GoTypeName getTypeName() {
    return findChildByClass(GoTypeName.class);
  }

}
