package fr.jg.aspergus.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.builder.DisplayMode;
import net.sf.jconverse.crud.builder.Finder;
import net.sf.jconverse.crud.field.Hints;
import net.sourceforge.cristalmodel.annotations.DisplayConstraints;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class VenteDetail extends LigneDetail {

  private Client client;

  @Order(600)
  @ManyToOne()
  @Uneditable
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Override
  @DisplayConstraints
  public void setupConstraints(final Hints hints, final DisplayMode mode, final Finder f) {
    super.setupConstraints(hints, mode, f);
  }

}
