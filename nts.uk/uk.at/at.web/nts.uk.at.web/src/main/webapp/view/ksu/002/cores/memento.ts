/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.memento {
	export interface MementoObservableArray<T> extends KnockoutObservableArray<T> {
		undo(): void;
		redo(): void;
		reset(data?: any): void;
		memento(data?: any): void;
		undoAble: KnockoutComputed<boolean>;
		redoAble: KnockoutComputed<boolean>;
	}

	export interface Options {
		size: number;
		replace?: (replacer: any) => any;
	}

	interface Memento {
		undo: KnockoutObservableArray<any>;
		redo: KnockoutObservableArray<any>;
	}

	const memento = function (target: KnockoutObservableArray<any>, options: Options) {
		if (!options) {
			options = {
				size: 9999
			};
		}

		if (options.size < 1) {
			options.size = 9999;
		}

		const $memento: Memento = {
			undo: ko.observableArray([]),
			redo: ko.observableArray([])
		};

		// strip length of memento if over size
		const stripMemory = () => {
			while (ko.unwrap($memento.undo).length > options.size) {
				$memento.undo.pop();
			}

			while (ko.unwrap($memento.redo).length > options.size) {
				$memento.redo.pop();
			}
		};

		// extends memento methods to observable
		_.extend(target, {
			reset: function $$reset(data?: any[]) {
				if (data !== undefined) {
					target(data);
				}

				$memento.undo([]);
				$memento.redo([]);
			},
			memento: function $$memento(data?: any) {
				$memento.redo([]);
				// push old data to memories			
				$memento.undo.unshift(ko.toJS(target));

				if (data !== undefined) {
					target(data);
				}

				// remove old record when memory size has large than config
				stripMemory();
			},
			undo: function $$undo() {
				if (ko.unwrap($memento.undo).length) {
					const current = ko.unwrap(target);
					const preview = $memento.undo.shift();

					$memento.redo.unshift(current);

					// remove old record when memory size has large than config
					stripMemory();

					if (!options.replace) {
						target(preview);
					} else {
						target(options.replace(preview));
					}
				}
			},
			undoAble: ko.computed(() => !!ko.unwrap($memento.undo).length),
			redo: function $$redo() {
				if (ko.unwrap($memento.redo).length) {
					const current = ko.unwrap(target);
					const forward = $memento.redo.shift();

					$memento.undo.unshift(current);

					// remove old record when memory size has large than config
					stripMemory();

					if (!options.replace) {
						target(forward);
					} else {
						target(options.replace(forward));
					}
				}
			},
			redoAble: ko.computed(() => !!ko.unwrap($memento.redo).length)
		});

		return target;
	};

	// register memento to ko static
	_.extend(ko.extenders, { memento });
}