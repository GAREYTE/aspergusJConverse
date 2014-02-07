package net.jconverse.template;

import net.sf.jasql.ds.DerbyEmbededSource;

public class TemplateDerbySource extends DerbyEmbededSource {

  private static TemplateDerbySource source;

  public static TemplateDerbySource getInstance() {
    if (source == null) {
      source = new TemplateDerbySource();
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