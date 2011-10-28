/*
 * Copyright (c) 2007-2009, Osmorc Development Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright notice, this list
 *       of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this
 *       list of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *     * Neither the name of 'Osmorc Development Team' nor the names of its contributors may be
 *       used to endorse or promote products derived from this software without specific
 *       prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.osmorc.manifest.lang.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.osmorc.manifest.ManifestFileTypeFactory;
import org.osmorc.manifest.lang.psi.Header;
import org.osmorc.manifest.lang.psi.ManifestFile;

/**
 * @author Robert F. Beeger (robert@beeger.net)
 */
public class ManifestFileImpl extends PsiFileBase implements ManifestFile {
  private static final Header[] EMPTY_ARRAY = new Header[0];

  public ManifestFileImpl(FileViewProvider viewProvider) {
    super(viewProvider, ManifestFileTypeFactory.MANIFEST.getLanguage());
  }

  @NotNull
  public FileType getFileType() {
    return ManifestFileTypeFactory.MANIFEST;
  }

  @Override
  public String toString() {
    return "ManifestFile:" + getName();
  }

  @NotNull
  @Override
  public Header[] getHeaders() {
    Header[] childrenOfType = PsiTreeUtil.getChildrenOfType(getFirstChild(), Header.class);
    return childrenOfType == null ? EMPTY_ARRAY : childrenOfType;
  }

  @Override
  public Header getHeaderByName(@NotNull String name) {

    Header childOfType = PsiTreeUtil.findChildOfType(getFirstChild(), Header.class);
    while (childOfType != null) {
      if (name.equals(childOfType.getName())) {
        return childOfType;
      }
      childOfType = PsiTreeUtil.getNextSiblingOfType(childOfType, Header.class);
    }
    return null;
  }
}
