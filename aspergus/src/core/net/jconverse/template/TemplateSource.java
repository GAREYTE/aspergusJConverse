package net.jconverse.template;

import net.sf.jasql.ds.DerbyEmbededSource;

public class TemplateSource extends DerbyEmbededSource {

  private static TemplateSource source;

  public static TemplateSource getInstance() {
    if (source == null) {
      source = new TemplateSource();
    }
    return source;
  }

  @Override
  public String getDatabaseName() {
    return "c:/template.db";
  }

  @Override
  protected String getDefaultPassword() {
    return "402610b74e00b98e";
  }

  @Override
  protected String getDefaultUser() {
    return "appli";
  }

  @Override
  public String getName() {
    return getClass().getName();
  }

  @Override
  protected String getTestTable() {
    return "DbVersion";
  }

  @Override
  public boolean isProductionDataBase() {
    return false;
  }

}