var NtsSortableBindingHandler = (function () {
    function NtsSortableBindingHandler() {
        var _this = this;
        this.ITEMKEY = "ko_sortItem";
        this.INDEXKEY = "ko_sourceIndex";
        this.LISTKEY = "ko_sortList";
        this.PARENTKEY = "ko_parentList";
        this.DRAGKEY = "ko_dragItem";
        this.dataSet = ko.utils.domData.set;
        this.dataGet = ko.utils.domData.get;
        this.version = $.ui && $.ui.version;
        this.hasNestedSortableFix = function () { return _this.version && _this.version.indexOf("1.6.") && _this.version.indexOf("1.7.") && (_this.version.indexOf("1.8.") || _this.version === "1.8.24"); };
        this.addMetaDataAfterRender = function (elements, data) {
            var self = _this;
            ko.utils.arrayForEach(elements, function (element) {
                if (element.nodeType === 1) {
                    self.dataSet(element, self.ITEMKEY, data);
                    self.dataSet(element, self.PARENTKEY, self.dataGet(element.parentNode, self.LISTKEY));
                }
            });
        };
        this.updateIndexFromDestroyedItems = function (index, items) {
            var self = _this, unwrapped = ko.unwrap(items);
            if (unwrapped) {
                for (var i = 0; i < index; i++) {
                    if (unwrapped[i] && ko.unwrap(unwrapped[i]._destroy)) {
                        index++;
                    }
                }
            }
            return index;
        };
        this.stripTemplateWhitespace = function (element, name) {
            var self = _this, templateSource, templateElement;
            if (name) {
                templateElement = document.getElementById(name);
                if (templateElement) {
                    templateSource = new ko.templateSources.domElement(templateElement);
                    templateSource.text($.trim(templateSource.text()));
                }
            }
            else {
                $(element).contents().each(function () {
                    if (this && this.nodeType !== 1) {
                        element.removeChild(this);
                    }
                });
            }
        };
        this.prepareTemplateOptions = function (valueAccessor, dataName) {
            var self = _this, result = {}, options = ko.unwrap(valueAccessor()) || {}, actualAfterRender;
            if (options.data) {
                result[dataName] = options.data;
                result.name = options.template;
            }
            else {
                result[dataName] = valueAccessor();
            }
            ko.utils.arrayForEach(["afterAdd", "afterRender", "as", "beforeRemove", "includeDestroyed", "templateEngine", "templateOptions", "nodes"], function (option) {
                if (options.hasOwnProperty(option)) {
                    result[option] = options[option];
                }
                else if (ko.bindingHandlers['ntsSortable'].hasOwnProperty(option)) {
                    result[option] = ko.bindingHandlers['ntsSortable'][option];
                }
            });
            if (dataName === "foreach") {
                if (result.afterRender) {
                    actualAfterRender = result.afterRender;
                    result.afterRender = function (element, data) {
                        self.addMetaDataAfterRender.call(data, element, data);
                        actualAfterRender.call(data, element, data);
                    };
                }
                else {
                    result.afterRender = self.addMetaDataAfterRender;
                }
            }
            return result;
        };
        this.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
            var self = _this, $element = $(element), value = ko.unwrap(valueAccessor()) || {}, templateOptions = self.prepareTemplateOptions(valueAccessor, "foreach"), sortable = {}, startActual, updateActual;
            self.stripTemplateWhitespace(element, templateOptions.name);
            $.extend(true, sortable, ko.bindingHandlers['ntsSortable']);
            if (value.options && sortable.options) {
                ko.utils.extend(sortable.options, value.options);
                delete value.options;
            }
            else {
                sortable.options = sortable.options || {};
                ko.utils.extend(sortable.options, {
                    start: function () { },
                    update: function () { }
                });
            }
            ko.utils.extend(sortable, value);
            if (sortable.connectClass && (ko.isObservable(sortable.allowDrop) || typeof sortable.allowDrop == "function")) {
                ko.computed({
                    read: function () {
                        var value = ko.unwrap(sortable.allowDrop), shouldAdd = typeof value == "function" ? value.call(this, templateOptions.foreach) : value;
                        ko.utils.toggleDomNodeCssClass(element, sortable.connectClass, shouldAdd);
                    },
                    disposeWhenNodeIsRemoved: element
                }, _this);
            }
            else {
                ko.utils.toggleDomNodeCssClass(element, sortable.connectClass, sortable.allowDrop);
            }
            ko.bindingHandlers.template.init(element, function () {
                return templateOptions;
            }, allBindingsAccessor, viewModel, bindingContext);
            startActual = sortable.options.start;
            updateActual = sortable.options.update;
            if (!sortable.options.helper) {
                sortable.options.helper = function (e, ui) {
                    if (ui.is("tr")) {
                        ui.children().each(function () {
                            $(this).width($(this).width());
                        });
                    }
                    return ui;
                };
            }
            var createTimeout = setTimeout(function () {
                var dragItem;
                var originalReceive = sortable.options.receive;
                $element.sortable(ko.utils.extend(sortable.options, {
                    start: function (event, ui) {
                        var el = ui.item[0];
                        self.dataSet(el, self.INDEXKEY, ko.utils.arrayIndexOf(ui.item.parent().children(), el));
                        ui.item.find("input:focus").change();
                        if (startActual) {
                            startActual.apply(this, arguments);
                        }
                    },
                    receive: function (event, ui) {
                        if (typeof originalReceive === "function") {
                            originalReceive.call(this, event, ui);
                        }
                        dragItem = self.dataGet(ui.item[0], self.DRAGKEY);
                        if (dragItem) {
                            if (dragItem.clone) {
                                dragItem = dragItem.clone();
                            }
                            if (sortable.dragged) {
                                dragItem = sortable.dragged.call(this, dragItem, event, ui) || dragItem;
                            }
                        }
                    },
                    update: function (event, ui) {
                        var sourceParent, targetParent, sourceIndex, targetIndex, arg, el = ui.item[0], parentEl = ui.item.parent()[0], item = self.dataGet(el, self.ITEMKEY) || dragItem;
                        if (!item) {
                            $(el).remove();
                        }
                        dragItem = null;
                        if (item && (this === parentEl) || (!self.hasNestedSortableFix && $.contains(this, parentEl))) {
                            sourceParent = self.dataGet(el, self.PARENTKEY);
                            sourceIndex = self.dataGet(el, self.INDEXKEY);
                            targetParent = self.dataGet(el.parentNode, self.LISTKEY);
                            targetIndex = ko.utils.arrayIndexOf(ui.item.parent().children(), el);
                            if (!templateOptions.includeDestroyed) {
                                sourceIndex = self.updateIndexFromDestroyedItems(sourceIndex, sourceParent);
                                targetIndex = self.updateIndexFromDestroyedItems(targetIndex, targetParent);
                            }
                            if (sortable.beforeMove || sortable.afterMove) {
                                arg = {
                                    item: item,
                                    sourceParent: sourceParent,
                                    sourceParentNode: sourceParent && ui.sender || el.parentNode,
                                    sourceIndex: sourceIndex,
                                    targetParent: targetParent,
                                    targetIndex: targetIndex,
                                    cancelDrop: false
                                };
                                if (sortable.beforeMove) {
                                    sortable.beforeMove.call(this, arg, event, ui);
                                }
                            }
                            if (sourceParent) {
                                $(sourceParent === targetParent ? this : ui.sender || this).sortable("cancel");
                            }
                            else {
                                $(el).remove();
                            }
                            if (arg && arg.cancelDrop) {
                                return;
                            }
                            if (!sortable.hasOwnProperty("strategyMove") || sortable.strategyMove === false) {
                                if (targetIndex >= 0) {
                                    if (sourceParent) {
                                        sourceParent.splice(sourceIndex, 1);
                                        if (ko['processAllDeferredBindingUpdates']) {
                                            ko['processAllDeferredBindingUpdates']();
                                        }
                                        if (ko.options && ko.options.deferUpdates) {
                                            ko.tasks.runEarly();
                                        }
                                    }
                                    targetParent.splice(targetIndex, 0, item);
                                }
                                self.dataSet(el, self.ITEMKEY, null);
                            }
                            else {
                                if (targetIndex >= 0) {
                                    if (sourceParent) {
                                        if (sourceParent !== targetParent) {
                                            sourceParent.splice(sourceIndex, 1);
                                            targetParent.splice(targetIndex, 0, item);
                                            self.dataSet(el, self.ITEMKEY, null);
                                            ui.item.remove();
                                        }
                                        else {
                                            var underlyingList = ko.unwrap(sourceParent);
                                            if (sourceParent.valueWillMutate) {
                                                sourceParent.valueWillMutate();
                                            }
                                            underlyingList.splice(sourceIndex, 1);
                                            underlyingList.splice(targetIndex, 0, item);
                                            if (sourceParent.valueHasMutated) {
                                                sourceParent.valueHasMutated();
                                            }
                                        }
                                    }
                                    else {
                                        targetParent.splice(targetIndex, 0, item);
                                        self.dataSet(el, self.ITEMKEY, null);
                                        ui.item.remove();
                                    }
                                }
                            }
                            if (ko['processAllDeferredBindingUpdates']) {
                                ko['processAllDeferredBindingUpdates']();
                            }
                            if (sortable.afterMove) {
                                sortable.afterMove.call(this, arg, event, ui);
                            }
                        }
                        if (updateActual) {
                            updateActual.apply(this, arguments);
                        }
                    },
                    connectWith: false
                }));
                if (sortable.isEnabled !== undefined) {
                    ko.computed({
                        read: function () {
                            $element.sortable(ko.unwrap(sortable.isEnabled) ? "enable" : "disable");
                        },
                        disposeWhenNodeIsRemoved: element
                    });
                }
            }, 0);
            ko.utils.domNodeDisposal.addDisposeCallback(element, function () {
                if ($element.data("ui-sortable") || $element.data("sortable")) {
                    $element.sortable("destroy");
                }
                ko.utils.toggleDomNodeCssClass(element, sortable.connectClass, false);
                clearTimeout(createTimeout);
            });
            return {
                'controlsDescendantBindings': true
            };
        };
        this.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
            var self = _this, templateOptions = self.prepareTemplateOptions(valueAccessor, "foreach");
            self.dataSet(element, self.LISTKEY, templateOptions.foreach);
            ko.bindingHandlers['template'].update(element, function () { return templateOptions; }, allBindingsAccessor, viewModel, bindingContext);
        };
    }
    return NtsSortableBindingHandler;
}());
ko.bindingHandlers["ntsSortable"] = new NtsSortableBindingHandler();
//# sourceMappingURL=sortable-ko-ext.js.map