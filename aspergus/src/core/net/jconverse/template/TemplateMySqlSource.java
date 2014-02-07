package net.jconverse.template;

import net.sf.jasql.ds.MySQLSource;

public class TemplateMySqlSource extends MySQLSource {

  private static TemplateMySqlSource source;

  public static TemplateMySqlSource getInstance() {
    if (source == null) {
      source = new TemplateMySqlSource();
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