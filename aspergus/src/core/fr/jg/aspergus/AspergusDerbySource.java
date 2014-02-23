package fr.jg.aspergus;

import net.sf.jasql.ds.DerbyEmbededSource;

public class AspergusDerbySource extends DerbyEmbededSource {

  private static AspergusDerbySource source;

  public static AspergusDerbySource getInstance() {
    if (source == null) {
      source = new AspergusDerbySource();
    }
    return source;
  }

  @Override
  public String getDatabaseName() {
    return "/Aspergus/Aspergus.db";
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