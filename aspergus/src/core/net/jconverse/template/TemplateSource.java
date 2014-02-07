package net.jconverse.template;

import net.sf.jasql.ds.MySQLSource;

public class TemplateSource extends MySQLSource {

  private static TemplateSource source;

  public static TemplateSource getInstance() {
    if (source == null) {
      source = new TemplateSource();
    }
    return source;
  }

  @Override
  public String getDatabaseName() {
    return "aspergus";
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

  @Override
  protected String getServerName() {
    // TODO Auto-generated method stub
    return "localhost";
  }

}