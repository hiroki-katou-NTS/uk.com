package uk.cnv.client.dom.fileimport;

import java.util.List;

import uk.cnv.client.infra.entity.JmGenAdd;

public interface JmGenAddRepository {

	List<JmGenAdd> findAll(int companyCode);
}
