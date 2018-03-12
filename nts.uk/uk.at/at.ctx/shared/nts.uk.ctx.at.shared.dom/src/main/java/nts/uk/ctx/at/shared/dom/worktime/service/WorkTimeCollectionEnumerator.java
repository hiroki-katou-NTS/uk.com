/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;

/**
 * The Class WorkTimeCollectionEnumerator.
 *
 * @param <E> the element type
 */
@RequiredArgsConstructor
public class WorkTimeCollectionEnumerator<E> {
	
	/** The maybe collection. */
	private final Object maybeCollection;
	
	/** The type of element. */
	private final Class<E> typeOfElement;
	
	/**
	 * Enumerate.
	 *
	 * @param <E> the element type
	 * @param maybeCollection the maybe collection
	 * @param typeOfElement the type of element
	 * @return the work time collection enumerator
	 */
	public static <E> WorkTimeCollectionEnumerator<E> enumerate(Object maybeCollection, Class<E> typeOfElement) {
		return new WorkTimeCollectionEnumerator<>(maybeCollection, typeOfElement);
	}
	
	/**
	 * If set.
	 *
	 * @param processor the processor
	 * @return the work time collection enumerator
	 */
	@SuppressWarnings("unchecked")
	public WorkTimeCollectionEnumerator<E> ifSet(Consumer<E> processor) {
		// Create new Bundled business exception.
		BundledBusinessException bundledBusinessException = BundledBusinessException.newInstance();
		
		if (this.maybeCollection instanceof Set<?>) {
			((Set<E>)this.maybeCollection).stream().forEach(element -> {
				if (this.typeOfElement.isAssignableFrom(element.getClass())) {
					// Try catch bussiness exception.
					try {
						processor.accept(element);
					} catch (BundledBusinessException e) {
						bundledBusinessException.addMessage(e.cloneExceptions());
						return;
					} catch (BusinessException e) {
						bundledBusinessException.addMessage(e);
						return;
					}
				}
			});
		}
		
		// Throw BundledBusinessException.
		if (!bundledBusinessException.cloneExceptions().isEmpty()) {
			throw bundledBusinessException;
		}
		return this;
	}
	
	/**
	 * If list.
	 *
	 * @param processor the processor
	 * @return the work time collection enumerator
	 */
	public WorkTimeCollectionEnumerator<E> ifList(BiConsumer<E, Integer> processor) {
		// Create new Bundled business exception.
		BundledBusinessException bundledBusinessException = BundledBusinessException.newInstance();
		
		if (this.maybeCollection instanceof List<?>) {
			@SuppressWarnings("unchecked")
			val collection = (List<E>)this.maybeCollection;
			for (int i = 0; i < collection.size(); i++) {
				val element = collection.get(i);
				if (this.typeOfElement.isAssignableFrom(element.getClass())) {
					// Try catch bussiness exception.
					try {
						processor.accept(element, i);
					} catch (BundledBusinessException e) {
						bundledBusinessException.addMessage(e.cloneExceptions());
						continue;
					} catch (BusinessException e) {
						bundledBusinessException.addMessage(e);
						continue;
					}
				}
			}
		}
		
		// Throw BundledBusinessException.
		if (!bundledBusinessException.cloneExceptions().isEmpty()) {
			throw bundledBusinessException;
		}
		return this;
	}
	
	/**
	 * If map.
	 *
	 * @param processor the processor
	 * @return the work time collection enumerator
	 */
	@SuppressWarnings("unchecked")
	public WorkTimeCollectionEnumerator<E> ifMap(Consumer<Map.Entry<?, E>> processor) {
		// Create new Bundled business exception.
		BundledBusinessException bundledBusinessException = BundledBusinessException.newInstance();
		
		if (this.maybeCollection instanceof Map<?, ?>) {
			((Map<?, E>)this.maybeCollection).entrySet().stream().forEach(entry -> {
				if (this.typeOfElement.isAssignableFrom(entry.getValue().getClass())) {
					// Try catch bussiness exception.
					try {
						processor.accept(entry);
					} catch (BundledBusinessException e) {
						bundledBusinessException.addMessage(e.cloneExceptions());
						return;
					} catch (BusinessException e) {
						bundledBusinessException.addMessage(e);
						return;
					}
				}
			});
		}
		
		// Throw BundledBusinessException.
		if (!bundledBusinessException.cloneExceptions().isEmpty()) {
			throw bundledBusinessException;
		}
		return this;
	}
}
