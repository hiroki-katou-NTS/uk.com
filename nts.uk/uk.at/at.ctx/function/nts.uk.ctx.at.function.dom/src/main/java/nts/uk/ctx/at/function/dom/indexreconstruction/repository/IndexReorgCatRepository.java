package nts.uk.ctx.at.function.dom.indexreconstruction.repository;

import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgCat;

import java.util.List;
import java.util.Optional;

/**
 * The interface IndexReorgCatRepository.<br>
 * Repository インデックス再構成カテゴリ
 */
public interface IndexReorgCatRepository {

	/**
	 * Finds all.
	 *
	 * @return the <code>IndexReorgCat</code> domain list
	 */
	List<IndexReorgCat> findAll();

	/**
	 * Find one.
	 *
	 * @param categoryNo the category no
	 * @return the optional
	 */
	Optional<IndexReorgCat> findOne(int categoryNo);

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(IndexReorgCat domain);

	/**
	 * Delete.
	 *
	 * @param categoryNo the category no
	 */
	void delete(int categoryNo);

}
