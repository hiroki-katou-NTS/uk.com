var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsGrid;
                (function (ntsGrid) {
                    var storage;
                    var dist;
                    (function (dist) {
                        dist.REMOTE = "Remote";
                        function query(features) {
                            storage = new Local();
                            var store = feature.find(features, feature.STORAGE);
                            if (!store)
                                return;
                            if (store.type === dist.REMOTE) {
                                storage = new Remote(store.loadPath, store.savePath);
                            }
                        }
                        dist.query = query;
                        var Local = (function () {
                            function Local() {
                            }
                            Local.prototype.getItem = function (key) {
                                var dfd = $.Deferred();
                                dfd.resolve(uk.localStorage.getItem(key));
                                return dfd.promise();
                            };
                            Local.prototype.setItemAsJson = function (key, value) {
                                var dfd = $.Deferred();
                                uk.localStorage.setItemAsJson(key, value);
                                dfd.resolve(true);
                                return dfd.promise();
                            };
                            return Local;
                        }());
                        dist.Local = Local;
                        var Remote = (function () {
                            function Remote(loadPath, savePath) {
                                this.loadPath = loadPath;
                                this.savePath = savePath;
                            }
                            Remote.prototype.getItem = function (key) {
                                var dfd = $.Deferred();
                                uk.request.ajax(this.loadPath, { value: key }).done(function (widths) {
                                    dfd.resolve(uk.util.optional.of(widths));
                                });
                                return dfd.promise();
                            };
                            Remote.prototype.setItemAsJson = function (key, value) {
                                var dfd = $.Deferred();
                                uk.request.ajax(this.savePath, { key: key, columns: value }).done(function (res) {
                                    dfd.resolve(res);
                                });
                                return dfd.promise();
                            };
                            return Remote;
                        }());
                        dist.Remote = Remote;
                    })(dist || (dist = {}));
                    $.fn.ntsGrid = function (options) {
                        var self = this;
                        var $self = $(self);
                        if (typeof options === "string") {
                            return functions.ntsAction($self, options, [].slice.call(arguments).slice(1));
                        }
                        if (options.ntsControls === undefined) {
                            $self.igGrid(options);
                            return;
                        }
                        dist.query(options.ntsFeatures);
                        if (options.hidePrimaryKey) {
                            _.forEach(options.columns, function (c) {
                                if (c.key === options.primaryKey) {
                                    c.width = "1px";
                                    if (columnSize.exists($self)) {
                                        columnSize.save($self, c.key, 1);
                                    }
                                    feature.merge(options, feature.RESIZING, columnSize.createResizeOptions(c.key));
                                    return false;
                                }
                            });
                        }
                        var flatCols = validation.scanValidators($self, options.columns);
                        var cellFormatter = $self.data(internal.CELL_FORMATTER);
                        if (!cellFormatter) {
                            cellFormatter = new color.CellFormatter($self, options.features, options.ntsFeatures, flatCols);
                            $self.data(internal.CELL_FORMATTER, cellFormatter);
                        }
                        $self.addClass('compact-grid nts-grid');
                        if ($self.closest(".nts-grid-wrapper").length === 0) {
                            $self.wrap($("<div class='nts-grid-wrapper'/>"));
                        }
                        var columnControlTypes = {};
                        var columnSpecialTypes = {};
                        var bounceCombos = {};
                        var cbHeaderColumns = [];
                        var cbSelectionColumns = {};
                        var formatColumn = function (column) {
                            if (column.hidden)
                                return column;
                            if (column.showHeaderCheckbox) {
                                column.headerText = ntsControls.createHeaderCheckbox({
                                    controlDef: {
                                        options: { value: 1, text: column.headerText },
                                        optionsValue: 'value',
                                        optionsText: 'text'
                                    }
                                }, column.key);
                                cbHeaderColumns.push(column.key);
                                cbSelectionColumns[column.key] = {
                                    selectAll: false, quantity: 0,
                                    onSelect: function (value) {
                                        var fs = this;
                                        var hiddenCount = fs.hiddenRows ? fs.hiddenRows.length : 0;
                                        var disableCount = fs.disableRows ? fs.disableRows.size : 0;
                                        if (value && ++fs.quantity === (options.dataSource.length - hiddenCount - disableCount)) {
                                            fs.th.find(".nts-grid-header-control-" + column.key).find("input[type='checkbox']").prop("checked", true);
                                            fs.selectAll = true;
                                        }
                                        else if (!value && fs.quantity > 0) {
                                            fs.quantity--;
                                            if (fs.selectAll) {
                                                fs.th.find(".nts-grid-header-control-" + column.key).find("input[type='checkbox']").prop("checked", false);
                                                fs.selectAll = false;
                                            }
                                        }
                                    }
                                };
                                if (column.hiddenRows) {
                                    cbSelectionColumns[column.key].hiddenRows = column.hiddenRows;
                                }
                            }
                            if (!uk.util.isNullOrUndefined(column.group)) {
                                var cols = _.map(column.group, formatColumn);
                                column.group = cols;
                                return column;
                            }
                            specialColumn.ifTrue(columnSpecialTypes, column, bounceCombos, flatCols);
                            if (column.ntsControl === undefined) {
                                columnControlTypes[column.key] = ntsControls.TEXTBOX;
                                return cellFormatter.format(column);
                            }
                            if (column.ntsControl === ntsControls.LABEL) {
                                ntsControls.drawLabel($self, column, cellFormatter);
                                columnControlTypes[column.key] = ntsControls.LABEL;
                                return cellFormatter.format(column, true);
                            }
                            var controlDef = _.find(options.ntsControls, function (ctl) {
                                return ctl.name === column.ntsControl;
                            });
                            if (!uk.util.isNullOrUndefined(controlDef))
                                columnControlTypes[column.key] = controlDef.controlType;
                            else {
                                columnControlTypes[column.key] = ntsControls.TEXTBOX;
                                return cellFormatter.format(column);
                            }
                            column.formatter = function (value, rowObj) {
                                if (uk.util.isNullOrUndefined(rowObj))
                                    return value;
                                var rowId = rowObj[$self.igGrid("option", "primaryKey")];
                                var update = function (val) {
                                    if (!uk.util.isNullOrUndefined($self.data("igGrid"))) {
                                        updating.updateCell($self, rowId, column.key, column.dataType !== 'string' ? val : val.toString());
                                        if (options.autoCommit === undefined || options.autoCommit === false) {
                                            var updatedRow = $self.igGrid("rowById", rowId, false);
                                            $self.igGrid("commit");
                                            if (updatedRow !== undefined)
                                                $self.igGrid("virtualScrollTo", $(updatedRow).data("row-idx"));
                                        }
                                    }
                                };
                                var deleteRow = function () {
                                    if ($self.data("igGrid") !== null) {
                                        $self.data("ntsRowDeleting", true);
                                        $self.data("igGridUpdating").deleteRow(rowId);
                                    }
                                };
                                var ntsControl = ntsControls.getControl(controlDef.controlType);
                                var $cell = internal.getCellById($self, rowId, column.key);
                                var isEnable;
                                if ($cell) {
                                    isEnable = $cell.find("." + ntsControl.containerClass()).data("enable");
                                }
                                isEnable = isEnable !== undefined ? isEnable : controlDef.enable === undefined ? true : controlDef.enable;
                                var data = {
                                    rowId: rowId,
                                    columnKey: column.key,
                                    controlDef: controlDef,
                                    update: update,
                                    deleteRow: deleteRow,
                                    initValue: value,
                                    rowObj: rowObj,
                                    showHeaderCheckbox: column.showHeaderCheckbox,
                                    enable: isEnable
                                };
                                if (!uk.util.isNullOrUndefined(column.tabIndex)) {
                                    data.tabIndex = column.tabIndex;
                                }
                                var back;
                                if (back = bounceCombos[column.key]) {
                                    data.bounce = back;
                                }
                                var controlCls = "nts-grid-control-" + column.key + "-" + rowId;
                                var $container = $("<div/>").append($("<div/>").addClass(controlCls).css("height", ntsControls.HEIGHT_CONTROL));
                                var $_self = $self;
                                setTimeout(function () {
                                    var $self = $_self;
                                    var rowId = rowObj[$self.igGrid("option", "primaryKey")];
                                    var $gridCell = internal.getCellById($self, rowId, column.key);
                                    var gridCellChild;
                                    if (!$gridCell || (gridCellChild = $gridCell.children()).length === 0)
                                        return;
                                    if (gridCellChild[0].children.length === 0) {
                                        if (controlDef.controlType !== ntsControls.CHECKBOX
                                            || !column.hiddenRows || !column.hiddenRows.some(function (v) { return v === rowId; })) {
                                            var $control = ntsControl.draw(data);
                                            var gridControl = $gridCell[0].querySelector("." + controlCls);
                                            if (!gridControl)
                                                return;
                                            gridControl.appendChild($control[0]);
                                            if (controlDef.controlType === ntsControls.CHECKBOX && column.showHeaderCheckbox) {
                                                var cbSelectCols = $self.data(internal.CB_SELECTED) || {};
                                                var cbColConf_1 = cbSelectCols[column.key];
                                                if (cbColConf_1) {
                                                    $control.on("change", function () {
                                                        cbColConf_1.onSelect($(this).find("input[type='checkbox']").is(":checked"));
                                                    });
                                                }
                                            }
                                        }
                                        ntsControl.$containedGrid = $self;
                                        var c = {
                                            id: rowId,
                                            columnKey: column.key,
                                            $element: $gridCell,
                                            element: $gridCell[0]
                                        };
                                        cellFormatter.style($self, c);
                                        color.rememberDisabled($self, c);
                                        color.markIfEdit($self, c);
                                    }
                                }, 0);
                                return $container.html();
                            };
                            return column;
                        };
                        var columns = _.map(options.columns, formatColumn);
                        options.columns = columns;
                        updating.addFeature(options);
                        options.autoCommit = true;
                        options.tabIndex = -1;
                        events.onCellClick($self);
                        settings.build($self, options);
                        copyPaste.ifOn($self, options);
                        events.afterRendered(options, cbSelectionColumns);
                        columnSize.init($self, options.columns);
                        ntsControls.bindCbHeaderColumns(options, cbHeaderColumns, cbSelectionColumns);
                        $self.data(internal.CONTROL_TYPES, columnControlTypes);
                        $self.data(internal.SPECIAL_COL_TYPES, columnSpecialTypes);
                        sheet.load.setup($self, options);
                        if (!onDemand.initial($self, options)) {
                            if (!$self.data(internal.ORIG_DS)) {
                                $self.data(internal.ORIG_DS, _.cloneDeep(options.dataSource));
                            }
                            $self.igGrid(options);
                        }
                        $(window).resize(function () {
                            if (options.autoFitWindow) {
                                settings.setGridSize($self);
                            }
                            columnSize.load($self);
                        });
                        $(document).on(events.Handler.CLICK, function (evt) {
                            if (!utils.isIgGrid($self) || !utils.isEditMode($self))
                                return;
                            var $fixedBodyContainer = $self.igGrid("fixedBodyContainer");
                            if (($fixedBodyContainer.length > 0 && utils.outsideGrid($fixedBodyContainer, evt.target)
                                && utils.outsideGrid($self, evt.target))
                                || ($fixedBodyContainer.length === 0 && utils.outsideGrid($self, evt.target))) {
                                updating.endEdit($self);
                            }
                        });
                    };
                    var feature;
                    (function (feature_1) {
                        feature_1.UPDATING = "Updating";
                        feature_1.SELECTION = "Selection";
                        feature_1.RESIZING = "Resizing";
                        feature_1.COLUMN_FIX = "ColumnFixing";
                        feature_1.PAGING = "Paging";
                        feature_1.COPY_PASTE = "CopyPaste";
                        feature_1.CELL_EDIT = "CellEdit";
                        feature_1.CELL_COLOR = "CellColor";
                        feature_1.CELL_STATE = "CellState";
                        feature_1.ROW_STATE = "RowState";
                        feature_1.TEXT_COLOR = "TextColor";
                        feature_1.TEXT_STYLE = "TextStyle";
                        feature_1.HEADER_STYLES = "HeaderStyles";
                        feature_1.HIDING = "Hiding";
                        feature_1.SHEET = "Sheet";
                        feature_1.DEMAND_LOAD = "LoadOnDemand";
                        feature_1.STORAGE = "Storage";
                        function replaceBy(options, featureName, newFeature) {
                            var replaceId;
                            _.forEach(options.features, function (feature, id) {
                                if (feature.name === featureName) {
                                    replaceId = id;
                                    return false;
                                }
                            });
                            options.features.splice(replaceId, 1, newFeature);
                        }
                        feature_1.replaceBy = replaceBy;
                        function merge(options, featureName, feature) {
                            var findId = -1;
                            var obj;
                            _.forEach(options.features, function (f, id) {
                                if (f.name === featureName) {
                                    obj = f;
                                    findId = id;
                                    return false;
                                }
                            });
                            if (findId > -1) {
                                _.merge(obj, feature);
                                options.features.splice(findId, 1, obj);
                            }
                            else {
                                options.features.push(feature);
                            }
                        }
                        feature_1.merge = merge;
                        function isEnable(features, name) {
                            return _.find(features, function (feature) {
                                return feature.name === name;
                            }) !== undefined;
                        }
                        feature_1.isEnable = isEnable;
                        function find(features, name) {
                            return _.find(features, function (feature) {
                                return feature.name === name;
                            });
                        }
                        feature_1.find = find;
                    })(feature || (feature = {}));
                    var updating;
                    (function (updating) {
                        updating.INPUT_CURR_SYM = "input-currency-symbol";
                        updating.CURR_SYM = "currency-symbol";
                        function addFeature(options) {
                            var updateFeature = createUpdateOptions(options);
                            if (!feature.isEnable(options.features, feature.UPDATING)) {
                                options.features.push(updateFeature);
                            }
                            else {
                                feature.replaceBy(options, feature.UPDATING, createUpdateOptions(options));
                            }
                        }
                        updating.addFeature = addFeature;
                        function createUpdateOptions(options) {
                            var updateFeature = { name: feature.UPDATING, enableAddRow: false, enableDeleteRow: false, editMode: 'none' };
                            if (feature.isEnable(options.ntsFeatures, feature.CELL_EDIT)) {
                                updateFeature.editMode = "cell";
                                updateFeature.editCellStarting = startEditCell;
                                updateFeature.editCellStarted = editStarted;
                                updateFeature.editCellEnding = beforeFinishEditCell;
                            }
                            return updateFeature;
                        }
                        function containsNtsControl($target) {
                            var td = $target;
                            if (!$target.prev().is("td"))
                                td = $target.closest("td");
                            return td.find("div[class*='nts-grid-control']").length > 0;
                        }
                        updating.containsNtsControl = containsNtsControl;
                        function startEditCell(evt, ui) {
                            var selectedCell = selection.getSelectedCell($(evt.target));
                            if (containsNtsControl($(evt.currentTarget)) || utils.isEnterKey(evt) || utils.isTabKey(evt)) {
                                if ($(evt.currentTarget).find("div[class*='nts-editor-container']").length > 0)
                                    return false;
                                if (uk.util.isNullOrUndefined(selectedCell) || !utils.selectable($(evt.target)))
                                    return;
                                $(evt.target).igGridSelection("selectCell", selectedCell.rowIndex, selectedCell.index, utils.isFixedColumnCell(selectedCell, utils.getVisibleColumnsMap($(evt.target))));
                                return false;
                            }
                            else if (utils.disabled($(evt.currentTarget)))
                                return false;
                            if (uk.util.isNullOrUndefined(selectedCell) || !utils.selectable($(evt.target)))
                                return;
                            var $cell = $(selectedCell.element);
                            if ($cell.hasClass(updating.CURR_SYM))
                                $cell.removeClass(updating.CURR_SYM);
                            return true;
                        }
                        function editStarted(evt, ui) {
                            var $grid = $(ui.owner.element);
                            var valueType = validation.getValueType($grid, ui.columnKey);
                            if (!evt.currentTarget) {
                                if (valueType === "TimeWithDay" || valueType === "Clock") {
                                    var $editor = $(ui.editor.find("input")[0]);
                                    $editor.css("text-align", "right");
                                }
                                else if (valueType === "Currency") {
                                    ui.editor.addClass(updating.INPUT_CURR_SYM);
                                    var $editor = $(ui.editor.find("input")[0]);
                                    $editor.css("text-align", "right");
                                }
                                return;
                            }
                            if (!uk.util.isNullOrUndefined(ui.value) && !_.isEmpty(ui.value)) {
                                if (valueType === "TimeWithDay" || valueType === "Clock") {
                                    var formatted_1;
                                    try {
                                        formatted_1 = uk.time.minutesBased.clock.dayattr.create(uk.time.minutesBased.clock.dayattr.parseString(ui.value).asMinutes).shortText;
                                    }
                                    catch (e) {
                                        return;
                                    }
                                    setTimeout(function () {
                                        var $editor = $(ui.editor.find("input")[0]);
                                        $editor.css("text-align", "right");
                                        $editor.val(formatted_1).select();
                                    }, 140);
                                }
                                else if (valueType === "Currency") {
                                    var groupSeparator = validation.getGroupSeparator($grid, ui.columnKey) || ",";
                                    var value_1 = uk.text.replaceAll(ui.value, groupSeparator, "");
                                    setTimeout(function () {
                                        ui.editor.addClass(updating.INPUT_CURR_SYM);
                                        var $editor = $(ui.editor.find("input")[0]);
                                        var numb = Number(value_1);
                                        $editor.val(isNaN(numb) ? value_1 : numb).css("text-align", "right").select();
                                    }, 140);
                                }
                            }
                            else if (valueType === "Currency") {
                                ui.editor.addClass(updating.INPUT_CURR_SYM);
                                var $editor = $(ui.editor.find("input")[0]);
                                $editor.css("text-align", "right");
                            }
                        }
                        function onEditCell(evt, cell) {
                            var $grid = fixedColumns.realGridOf($(evt.currentTarget));
                            if (!utils.isEditMode($grid))
                                return;
                            var validators = $grid.data(validation.VALIDATORS);
                            var fieldValidator = validators[cell.columnKey];
                            if (uk.util.isNullOrUndefined(fieldValidator))
                                return;
                            var cellValue = $(cell.element).find("input:first").val();
                            var result = fieldValidator.probe(cellValue);
                            var $cellContainer = $(cell.element);
                            errors.clear($grid, cell);
                            if (!result.isValid) {
                                errors.set($grid, cell, result.errorMessage);
                            }
                        }
                        updating.onEditCell = onEditCell;
                        function triggerCellUpdate(evt, cell) {
                            var grid = evt.currentTarget;
                            var $targetGrid = fixedColumns.realGridOf($(grid));
                            if (utils.isEditMode($targetGrid) || utils.disabled($(cell.element)))
                                return;
                            if (utils.isAlphaNumeric(evt) || utils.isMinusSymbol(evt)
                                || utils.isDeleteKey(evt)) {
                                startEdit(evt, cell);
                            }
                        }
                        updating.triggerCellUpdate = triggerCellUpdate;
                        function startEdit(evt, cell) {
                            var $targetGrid = fixedColumns.realGridOf($(evt.currentTarget));
                            if (!utils.updatable($targetGrid))
                                return;
                            var $cell = $(cell.element);
                            if ($cell.hasClass(updating.CURR_SYM))
                                $cell.removeClass(updating.CURR_SYM);
                            utils.startEdit($targetGrid, cell);
                            if (!utils.isDeleteKey(evt)) {
                                setTimeout(function () {
                                    var cellValue;
                                    var char = evt.key === "Subtract" ? "-" : evt.key;
                                    var $editor = $targetGrid.igGridUpdating("editorForCell", $(cell.element));
                                    if (!uk.util.isNullOrUndefined($editor.data("igTextEditor"))) {
                                        $editor.igTextEditor("value", char);
                                        var input_1 = $editor.find("input")[0];
                                        var len_1 = input_1.value.length;
                                        if ($.ig.util.isChrome || $.ig.util.isSafari) {
                                            setTimeout(function () {
                                                input_1.setSelectionRange(len_1, len_1);
                                            }, 110);
                                        }
                                        else {
                                            input_1.setSelectionRange(len_1, len_1);
                                        }
                                        cellValue = char;
                                    }
                                    else if (!uk.util.isNullOrUndefined($editor.data("igNumericEditor"))) {
                                        cellValue = char;
                                        if (!utils.isMinusSymbol(evt)) {
                                            $editor.igNumericEditor("value", parseInt(cellValue));
                                        }
                                        else {
                                            cellValue = "-";
                                            $editor.igNumericEditor("value", cellValue);
                                        }
                                        if ($.ig.util.isChrome || $.ig.util.isSafari) {
                                            setTimeout(function () {
                                                var length = String($editor.igNumericEditor("value")).length;
                                                $editor.igNumericEditor("select", length, length);
                                            }, 110);
                                        }
                                        else {
                                            var length_1 = String($editor.igNumericEditor("value")).length;
                                            $editor.igNumericEditor("select", length_1, length_1);
                                        }
                                    }
                                    var validators = $targetGrid.data(validation.VALIDATORS);
                                    var fieldValidator = validators[cell.columnKey];
                                    if (uk.util.isNullOrUndefined(fieldValidator))
                                        return;
                                    var result = fieldValidator.probe(cellValue);
                                    var $cellContainer = $(cell.element);
                                    errors.clear($targetGrid, cell);
                                    if (!result.isValid) {
                                        errors.set($targetGrid, cell, result.errorMessage);
                                    }
                                }, 1);
                            }
                            else {
                                setTimeout(function () {
                                    var $editor = $targetGrid.igGridUpdating("editorForCell", $(cell.element));
                                    $editor.find("input").val("");
                                }, 1);
                            }
                            evt.preventDefault();
                            evt.stopImmediatePropagation();
                        }
                        function beforeFinishEditCell(evt, ui) {
                            var $grid = $(evt.target);
                            var selectedCell = selection.getSelectedCell($grid);
                            var settings = $grid.data(internal.SETTINGS);
                            if (settings.preventEditInError
                                && utils.isEditMode($grid) && errors.any(selectedCell)) {
                                return false;
                            }
                            if (utils.isEditMode($grid) && (utils.isTabKey(evt) || utils.isEnterKey(evt) || evt.keyCode === undefined)) {
                                var gridUpdate_1 = $grid.data("igGridUpdating");
                                var origValues = gridUpdate_1._originalValues;
                                if (!uk.util.isNullOrUndefined(origValues)) {
                                    _.forEach(Object.keys(origValues), function (colKey, idx) {
                                        if (idx === 0) {
                                            gridUpdate_1._originalValues[colKey] = ui.value;
                                            return false;
                                        }
                                    });
                                    _.defer(function () {
                                        updating.updateCell($grid, selectedCell.id, selectedCell.columnKey, ui.value);
                                    });
                                }
                            }
                            var $editorContainer = $(selectedCell.element).find(errors.EDITOR_SELECTOR);
                            if ($editorContainer.length > 0)
                                $editorContainer.css(errors.NO_ERROR_STL);
                            specialColumn.tryDo($grid, selectedCell, ui.value);
                            if (ui.editor.hasClass(updating.INPUT_CURR_SYM)) {
                                $(selectedCell.element).addClass(updating.CURR_SYM);
                            }
                            return true;
                        }
                        function _updateRow($grid, rowId, visibleColumnsMap, updatedRowData) {
                            if (uk.util.isNullOrUndefined(updatedRowData) || Object.keys(updatedRowData).length === 0)
                                return;
                            $grid.igGridUpdating("updateRow", utils.parseIntIfNumber(rowId, $grid, visibleColumnsMap), updatedRowData);
                        }
                        updating._updateRow = _updateRow;
                        function updateCell($grid, rowId, columnKey, cellValue, allColumnsMap, forceRender) {
                            var grid = $grid.data("igGrid");
                            if (!utils.updatable($grid))
                                return;
                            var gridUpdate = $grid.data("igGridUpdating");
                            var origDs = $grid.data(internal.ORIG_DS);
                            var autoCommit = grid.options.autoCommit;
                            var columnsMap = allColumnsMap || utils.getColumnsMap($grid);
                            var rId = utils.parseIntIfNumber(rowId, $grid, columnsMap);
                            var valueType = validation.getValueType($grid, columnKey);
                            if (!uk.util.isNullOrUndefined(cellValue) && !_.isEmpty(cellValue)
                                && (valueType === "TimeWithDay" || valueType === "Clock")) {
                                try {
                                    cellValue = uk.time.minutesBased.clock.dayattr.create(uk.time.minutesBased.clock.dayattr.parseString(String(cellValue)).asMinutes).shortText;
                                }
                                catch (e) { }
                            }
                            var setting = $grid.data(internal.SETTINGS);
                            var idx = setting.descriptor.keyIdxes[rId];
                            if (uk.util.isNullOrUndefined(idx))
                                return;
                            var origData = origDs[idx];
                            grid.dataSource.setCellValue(rId, columnKey, cellValue, autoCommit);
                            var isControl = utils.isNtsControl($grid, columnKey);
                            if (!isControl || forceRender)
                                renderCell($grid, rId, columnKey);
                            if (isControl) {
                                $grid.trigger(events.Handler.CONTROL_CHANGE, [{ columnKey: columnKey, value: cellValue }]);
                            }
                            gridUpdate._notifyCellUpdated(rId);
                            notifyUpdate($grid, rowId, columnKey, cellValue, origData);
                        }
                        updating.updateCell = updateCell;
                        function updateRow($grid, rowId, updatedRowData, allColumnsMap, forceRender) {
                            var grid = $grid.data("igGrid");
                            if (!utils.updatable($grid))
                                return;
                            var gridUpdate = $grid.data("igGridUpdating");
                            var autoCommit = grid.options.autoCommit;
                            var columnsMap = allColumnsMap || utils.getColumnsMap($grid);
                            var rId = utils.parseIntIfNumber(rowId, $grid, columnsMap);
                            var origDs = $grid.data(internal.ORIG_DS);
                            var setting = $grid.data(internal.SETTINGS);
                            var idx = setting.descriptor.keyIdxes[rId];
                            if (uk.util.isNullOrUndefined(idx))
                                return;
                            var origData = origDs[idx];
                            grid.dataSource.updateRow(rId, $.extend({}, gridUpdate._getLatestValues(rId), updatedRowData), autoCommit);
                            _.forEach(Object.keys(updatedRowData), function (key) {
                                notifyUpdate($grid, rowId, key, updatedRowData[key], origData);
                                var isControl = utils.isNtsControl($grid, key);
                                if (isControl) {
                                    $grid.trigger(events.Handler.CONTROL_CHANGE, [{ columnKey: key, value: updatedRowData[key] }]);
                                }
                                if (isControl && !forceRender)
                                    return;
                                var $vCell = renderCell($grid, rId, key, origData);
                                var validators = $grid.data(validation.VALIDATORS);
                                var fieldValidator = validators[key];
                                if (uk.util.isNullOrUndefined(fieldValidator))
                                    return;
                                var cellValue = updatedRowData[key];
                                var result = fieldValidator.probe(String(cellValue));
                                var cell = {
                                    id: rowId,
                                    columnKey: key,
                                    element: $vCell
                                };
                                errors.clear($grid, cell);
                                if (!result.isValid) {
                                    errors.set($grid, cell, result.errorMessage);
                                }
                            });
                            gridUpdate._notifyRowUpdated(rId, null);
                        }
                        updating.updateRow = updateRow;
                        function notifyUpdate($grid, rowId, columnKey, value, origData) {
                            if (origData && (origData[columnKey] === value
                                || (uk.util.isNullOrUndefined(origData[columnKey]) && _.isEmpty(value)))) {
                                var updatedCells_1 = $grid.data(internal.UPDATED_CELLS);
                                if (updatedCells_1) {
                                    _.remove(updatedCells_1, function (c, i) {
                                        return c.rowId === rowId && c.columnKey === columnKey;
                                    });
                                }
                                var options_1 = $grid.data(internal.GRID_OPTIONS);
                                if (!options_1 || !options_1.getUserId || !options_1.userId)
                                    return;
                                var id_1;
                                if (uk.util.isNullOrUndefined(id_1 = origData[options_1.primaryKey])) {
                                    var record = $grid.igGrid("findRecordByKey", rowId);
                                    if (!record)
                                        return;
                                    id_1 = record[options_1.primaryKey];
                                }
                                var userId_1 = options_1.getUserId(id_1);
                                var $cell_1 = internal.getCellById($grid, rowId, columnKey);
                                var cols = void 0;
                                if (userId_1 === options_1.userId) {
                                    $cell_1.removeClass(color.ManualEditTarget);
                                    var targetEdits = $grid.data(internal.TARGET_EDITS);
                                    if (targetEdits && (cols = targetEdits[rowId])) {
                                        _.remove(cols, function (c) { return c === columnKey; });
                                        if (cols.length === 0)
                                            delete targetEdits[rowId];
                                    }
                                }
                                else {
                                    $cell_1.removeClass(color.ManualEditOther);
                                    var otherEdits = $grid.data(internal.OTHER_EDITS);
                                    if (otherEdits && (cols = otherEdits[rowId])) {
                                        _.remove(cols, function (c) { return c === columnKey; });
                                        if (cols.length === 0)
                                            delete otherEdits[rowId];
                                    }
                                }
                                return;
                            }
                            var updatedCells = $grid.data(internal.UPDATED_CELLS);
                            if (!updatedCells) {
                                $grid.data(internal.UPDATED_CELLS, []);
                                updatedCells = $grid.data(internal.UPDATED_CELLS);
                            }
                            var index = -1;
                            var tCell = _.find(updatedCells, function (c, i) {
                                if (c.rowId === rowId && c.columnKey === columnKey) {
                                    index = i;
                                    return true;
                                }
                            });
                            if (tCell)
                                updatedCells[index].value = value;
                            else
                                updatedCells.push({ rowId: rowId, columnKey: columnKey, value: value });
                            var options = $grid.data(internal.GRID_OPTIONS);
                            if (!options || !options.getUserId || !options.userId)
                                return;
                            var id;
                            if (!origData || uk.util.isNullOrUndefined(id = origData[options.primaryKey])) {
                                var record = $grid.igGrid("findRecordByKey", rowId);
                                if (!record)
                                    return;
                                id = record[options.primaryKey];
                            }
                            var userId = options.getUserId(id);
                            var $cell = internal.getCellById($grid, rowId, columnKey);
                            if (userId === options.userId) {
                                $cell.addClass(color.ManualEditTarget);
                                var targetEdits = $grid.data(internal.TARGET_EDITS);
                                if (!targetEdits) {
                                    targetEdits = {};
                                    targetEdits[rowId] = [columnKey];
                                    $grid.data(internal.TARGET_EDITS, targetEdits);
                                    return;
                                }
                                if (!targetEdits[rowId]) {
                                    targetEdits[rowId] = [columnKey];
                                    return;
                                }
                                targetEdits[rowId].push(columnKey);
                            }
                            else {
                                $cell.addClass(color.ManualEditOther);
                                var otherEdits = $grid.data(internal.OTHER_EDITS);
                                if (!otherEdits) {
                                    otherEdits = {};
                                    otherEdits[rowId] = [columnKey];
                                    $grid.data(internal.OTHER_EDITS, otherEdits);
                                    return;
                                }
                                if (!otherEdits[rowId]) {
                                    otherEdits[rowId] = [columnKey];
                                    return;
                                }
                                otherEdits[rowId].push(columnKey);
                            }
                        }
                        function renderCell($grid, rowId, columnKey, latestValues, clearStates) {
                            var grid = $grid.data("igGrid");
                            if (!utils.updatable($grid))
                                return;
                            var gridUpdate = $grid.data("igGridUpdating");
                            var rowData = gridUpdate._getLatestValues(rowId);
                            var column = _.find(utils.getVisibleColumns($grid), function (col) {
                                return col.key === columnKey;
                            });
                            var $cell = $grid.igGrid("cellById", rowId, columnKey);
                            if (clearStates) {
                                [color.Error, color.Alarm, color.ManualEditTarget, color.ManualEditOther,
                                    color.Reflect, color.Calculation, color.Disable].forEach(function (s) {
                                    if ($cell.hasClass(s))
                                        $cell.removeClass(s);
                                });
                            }
                            $cell.html(String(grid._renderCell(rowData[columnKey], column, rowData)));
                            return $cell;
                        }
                        updating.renderCell = renderCell;
                        function endEdit($grid) {
                            var selectedCell = selection.getSelectedCell($grid);
                            var $selectedCell = $(selectedCell.element);
                            var $editorContainer = $selectedCell.find(errors.EDITOR_SELECTOR);
                            var value = $editorContainer.find("input")[0].value;
                            var settings = $grid.data(internal.SETTINGS);
                            if (settings.preventEditInError
                                && utils.isEditMode($grid) && errors.any(selectedCell)) {
                                return;
                            }
                            if (utils.isEditMode($grid)) {
                                var gridUpdate_2 = $grid.data("igGridUpdating");
                                var origValues = gridUpdate_2._originalValues;
                                if (!uk.util.isNullOrUndefined(origValues)) {
                                    _.forEach(Object.keys(origValues), function (colKey, idx) {
                                        if (idx === 0) {
                                            gridUpdate_2._originalValues[colKey] = value;
                                            return false;
                                        }
                                    });
                                    _.defer(function () {
                                        updating.updateCell($grid, selectedCell.id, selectedCell.columnKey, value);
                                    });
                                }
                            }
                            if ($editorContainer.length > 0)
                                $editorContainer.css(errors.NO_ERROR_STL);
                            specialColumn.tryDo($grid, selectedCell, value);
                            if ($editorContainer.find("span").hasClass(updating.INPUT_CURR_SYM)) {
                                $selectedCell.addClass(updating.CURR_SYM);
                            }
                            $grid.igGridUpdating("endEdit");
                        }
                        updating.endEdit = endEdit;
                    })(updating || (updating = {}));
                    var selection;
                    (function (selection_1) {
                        function addFeature(options) {
                            var selection = { name: feature.SELECTION, mode: "cell", multipleSelection: true, wrapAround: false, cellSelectionChanged: selectCellChange };
                            if (!feature.isEnable(options.features, feature.SELECTION)) {
                                options.features.push(selection);
                            }
                            else {
                                feature.replaceBy(options, feature.SELECTION, selection);
                            }
                        }
                        selection_1.addFeature = addFeature;
                        function selectBefore($grid, enterDirection) {
                            var enter = enterDirection || "right";
                            if (enter === "right")
                                selectPrev($grid);
                            else
                                selectAbove($grid);
                        }
                        selection_1.selectBefore = selectBefore;
                        function selectPrev($grid) {
                            var selectedCell = getSelectedCell($grid);
                            if (uk.util.isNullOrUndefined(selectedCell))
                                return;
                            clearSelection($grid);
                            var visibleColumnsMap = utils.getVisibleColumnsMap($grid);
                            var isFixed = utils.isFixedColumnCell(selectedCell, visibleColumnsMap);
                            if (selectedCell.index > 0) {
                                selectCell($grid, selectedCell.rowIndex, selectedCell.index - 1, isFixed);
                                var afterSelect = getSelectedCell($grid);
                                if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                    selectPrev($grid);
                                }
                            }
                            else if (selectedCell.index === 0) {
                                var columnsGroup = utils.columnsGroupOfCell(selectedCell, visibleColumnsMap);
                                if (uk.util.isNullOrUndefined(columnsGroup) || columnsGroup.length === 0)
                                    return;
                                var fixedColumns_1 = utils.getFixedColumns(visibleColumnsMap);
                                var unfixedColumns_1 = utils.getUnfixedColumns(visibleColumnsMap);
                                if (isFixed || !utils.fixable($grid)) {
                                    if (selectedCell.rowIndex > 0) {
                                        selectCell($grid, selectedCell.rowIndex - 1, unfixedColumns_1.length - 1);
                                    }
                                    else {
                                        var dataSource = $grid.igGrid("option", "dataSource");
                                        var sourceSize_1 = dataSource.length;
                                        $grid.igGrid("virtualScrollTo", sourceSize_1);
                                        setTimeout(function () {
                                            if (utils.pageable($grid)) {
                                                var pageSize = $grid.igGridPaging("pageSize");
                                                var pageIndex = $grid.igGridPaging("pageIndex");
                                                if (pageSize * (pageIndex + 1) > sourceSize_1) {
                                                    selectCell($grid, sourceSize_1 - pageSize * pageIndex - 1, unfixedColumns_1.length - 1);
                                                }
                                                else {
                                                    selectCell($grid, pageSize - 1, unfixedColumns_1.length - 1);
                                                }
                                                return;
                                            }
                                            selectCell($grid, sourceSize_1 - 1, unfixedColumns_1.length - 1);
                                        }, 1);
                                    }
                                }
                                else if (utils.fixable($grid) && !isFixed) {
                                    selectCell($grid, selectedCell.rowIndex, fixedColumns_1.length - 1, true);
                                }
                            }
                        }
                        selection_1.selectPrev = selectPrev;
                        function selectAbove($grid) {
                            var selectedCell = getSelectedCell($grid);
                            if (uk.util.isNullOrUndefined(selectedCell))
                                return;
                            clearSelection($grid);
                            var isFixed = utils.isFixedColumnCell(selectedCell, utils.getVisibleColumnsMap($grid));
                            var dataSource = $grid.igGrid("option", "dataSource");
                            var sourceSize = dataSource.length;
                            if (selectedCell.rowIndex > 0) {
                                selectCell($grid, selectedCell.rowIndex - 1, selectedCell.index, isFixed);
                            }
                            else if (selectedCell.rowIndex === 0) {
                                var visibleColumnsMap_1 = utils.getVisibleColumnsMap($grid);
                                var columnsGroup_1 = utils.columnsGroupOfCell(selectedCell, visibleColumnsMap_1);
                                if (uk.util.isNullOrUndefined(columnsGroup_1) || columnsGroup_1.length === 0)
                                    return;
                                $grid.igGrid("virtualScrollTo", sourceSize);
                                if (utils.pageable($grid)) {
                                    var pageSize = $grid.igGridPaging("pageSize");
                                    var pageIndex = $grid.igGridPaging("pageIndex");
                                    var lastIndex_1 = pageSize - 1;
                                    if (pageSize * (pageIndex + 1) > sourceSize) {
                                        lastIndex_1 = sourceSize - pageSize * pageIndex - 1;
                                    }
                                    setTimeout(function () {
                                        if (selectedCell.index > 0) {
                                            selectCell($grid, lastIndex_1, selectedCell.index - 1, columnsGroup_1[0].fixed);
                                        }
                                        else if (selectedCell.index === 0) {
                                            if (columnsGroup_1[0].fixed) {
                                                selectCell($grid, lastIndex_1, visibleColumnsMap_1["undefined"].length - 1);
                                                return;
                                            }
                                            var noOfColTypes = Object.keys(visibleColumnsMap_1).length;
                                            if (noOfColTypes === 2) {
                                                selectCell($grid, lastIndex_1, visibleColumnsMap_1["true"].length - 1, true);
                                            }
                                            else {
                                                selectCell($grid, lastIndex_1, visibleColumnsMap_1["undefined"].length - 1);
                                            }
                                        }
                                        var afterSelect = getSelectedCell($grid);
                                        if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                            selectPrev($grid);
                                            selectBelow($grid);
                                        }
                                    }, 1);
                                    return;
                                }
                                setTimeout(function () {
                                    if (selectedCell.index > 0) {
                                        selectCell($grid, sourceSize - 1, selectedCell.index - 1, columnsGroup_1[0].fixed);
                                    }
                                    else if (selectedCell.index === 0) {
                                        if (columnsGroup_1[0].fixed) {
                                            selectCell($grid, sourceSize - 1, visibleColumnsMap_1["undefined"].length - 1);
                                            return;
                                        }
                                        var noOfColTypes = Object.keys(visibleColumnsMap_1).length;
                                        if (noOfColTypes === 2) {
                                            selectCell($grid, sourceSize - 1, visibleColumnsMap_1["true"].length - 1, true);
                                        }
                                        else {
                                            selectCell($grid, sourceSize - 1, visibleColumnsMap_1["undefined"].length - 1);
                                        }
                                    }
                                    var afterSelect = getSelectedCell($grid);
                                    if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                        selectPrev($grid);
                                        selectBelow($grid);
                                    }
                                }, 1);
                            }
                        }
                        selection_1.selectAbove = selectAbove;
                        function selectFollow($grid, enterDirection) {
                            var enter = enterDirection || "right";
                            if (enter === "right")
                                selectNext($grid);
                            else
                                selectBelow($grid);
                        }
                        selection_1.selectFollow = selectFollow;
                        function selectNext($grid) {
                            var selectedCell = getSelectedCell($grid);
                            if (uk.util.isNullOrUndefined(selectedCell))
                                return;
                            clearSelection($grid);
                            var visibleColumnsMap = utils.getVisibleColumnsMap($grid);
                            var dataSource = $grid.igGrid("option", "dataSource");
                            var columnsGroup = utils.columnsGroupOfCell(selectedCell, visibleColumnsMap);
                            if (uk.util.isNullOrUndefined(columnsGroup) || columnsGroup.length === 0)
                                return;
                            if (selectedCell.index < columnsGroup.length - 1) {
                                selectCell($grid, selectedCell.rowIndex, selectedCell.index + 1, columnsGroup[0].fixed);
                            }
                            else if (selectedCell.index === columnsGroup.length - 1) {
                                if (columnsGroup[0].fixed) {
                                    selectCell($grid, selectedCell.rowIndex, 0);
                                }
                                else if (utils.pageable($grid)) {
                                    var pageSize = $grid.igGridPaging("pageSize");
                                    var pageIndex = $grid.igGridPaging("pageIndex");
                                    if ((dataSource.length < pageSize * (pageIndex + 1)
                                        && selectedCell.rowIndex < (dataSource.length - pageSize * pageIndex - 1))
                                        || selectedCell.rowIndex < (pageSize - 1)) {
                                        selectCell($grid, selectedCell.rowIndex + 1, 0, true);
                                        var afterSelect = getSelectedCell($grid);
                                        if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                            selectNext($grid);
                                        }
                                    }
                                    else {
                                        $grid.igGrid("virtualScrollTo", "0px");
                                        setTimeout(function () {
                                            selectCell($grid, 0, 0, utils.fixable($grid) ? true : false);
                                            var afterSelect = getSelectedCell($grid);
                                            if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                                selectNext($grid);
                                            }
                                        }, 1);
                                    }
                                }
                                else if (selectedCell.rowIndex < dataSource.length - 1) {
                                    selectCell($grid, selectedCell.rowIndex + 1, 0, true);
                                    var afterSelect = getSelectedCell($grid);
                                    if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                        selectNext($grid);
                                    }
                                }
                                else {
                                    $grid.igGrid("virtualScrollTo", "0px");
                                    setTimeout(function () {
                                        selectCell($grid, 0, 0, utils.fixable($grid) ? true : false);
                                        var afterSelect = getSelectedCell($grid);
                                        if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                            selectNext($grid);
                                        }
                                    }, 1);
                                }
                            }
                        }
                        function selectBelow($grid) {
                            var selectedCell = getSelectedCell($grid);
                            if (uk.util.isNullOrUndefined(selectedCell))
                                return;
                            clearSelection($grid);
                            var isFixed = utils.isFixedColumnCell(selectedCell, utils.getVisibleColumnsMap($grid));
                            var dataSource = $grid.igGrid("option", "dataSource");
                            var sourceSize = dataSource.length;
                            if (utils.pageable($grid)) {
                                var pageSize = $grid.igGridPaging("pageSize");
                                var pageIndex = $grid.igGridPaging("pageIndex");
                                if ((pageSize * (pageIndex + 1) > sourceSize
                                    && selectedCell.rowIndex < (sourceSize - pageSize * pageIndex - 1))
                                    || selectedCell.rowIndex < (pageSize - 1)) {
                                    selectCell($grid, selectedCell.rowIndex + 1, selectedCell.index, isFixed);
                                }
                                else {
                                    var visibleColumnsMap_2 = utils.getVisibleColumnsMap($grid);
                                    var columnsGroup_2 = utils.columnsGroupOfCell(selectedCell, visibleColumnsMap_2);
                                    if (uk.util.isNullOrUndefined(columnsGroup_2) || columnsGroup_2.length === 0)
                                        return;
                                    $grid.igGrid("virtualScrollTo", "0px");
                                    setTimeout(function () {
                                        if (selectedCell.index < columnsGroup_2.length - 1) {
                                            selectCell($grid, 0, selectedCell.index + 1, columnsGroup_2[0].fixed);
                                        }
                                        else if (selectedCell.index === columnsGroup_2.length - 1) {
                                            if (columnsGroup_2[0].fixed) {
                                                selectCell($grid, 0, 0);
                                            }
                                            else {
                                                selectCell($grid, 0, 0, Object.keys(visibleColumnsMap_2).length === 2 ? true : undefined);
                                            }
                                        }
                                        var afterSelect = getSelectedCell($grid);
                                        if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                            selectNext($grid);
                                        }
                                    }, 1);
                                }
                                return;
                            }
                            if (selectedCell.rowIndex < sourceSize - 1) {
                                selectCell($grid, selectedCell.rowIndex + 1, selectedCell.index, isFixed);
                            }
                            else if (selectedCell.rowIndex === sourceSize - 1) {
                                var visibleColumnsMap_3 = utils.getVisibleColumnsMap($grid);
                                var columnsGroup_3 = utils.columnsGroupOfCell(selectedCell, visibleColumnsMap_3);
                                if (uk.util.isNullOrUndefined(columnsGroup_3) || columnsGroup_3.length === 0)
                                    return;
                                $grid.igGrid("virtualScrollTo", "0px");
                                setTimeout(function () {
                                    if (selectedCell.index < columnsGroup_3.length - 1) {
                                        selectCell($grid, 0, selectedCell.index + 1, columnsGroup_3[0].fixed);
                                    }
                                    else if (selectedCell.index === columnsGroup_3.length - 1) {
                                        if (columnsGroup_3[0].fixed) {
                                            selectCell($grid, 0, 0);
                                        }
                                        else {
                                            selectCell($grid, 0, 0, Object.keys(visibleColumnsMap_3).length === 2 ? true : undefined);
                                        }
                                    }
                                    var afterSelect = getSelectedCell($grid);
                                    if (afterSelect && $(afterSelect.element).outerWidth() === 1) {
                                        selectNext($grid);
                                    }
                                }, 1);
                            }
                        }
                        function getSelectedCell($grid) {
                            if (!utils.selectable($grid)) {
                                var $targetGrid = fixedColumns.realGridOf($grid);
                                if (!uk.util.isNullOrUndefined($targetGrid)) {
                                    return $targetGrid.igGridSelection("selectedCells")[0] || $targetGrid.data(internal.SELECTED_CELL);
                                }
                            }
                            return $grid.igGridSelection("selectedCells")[0] || $grid.data(internal.SELECTED_CELL);
                        }
                        selection_1.getSelectedCell = getSelectedCell;
                        function getSelectedCells($grid) {
                            return utils.selectable($grid) ? $grid.igGridSelection("selectedCells") : undefined;
                        }
                        selection_1.getSelectedCells = getSelectedCells;
                        function selectCell($grid, rowIndex, columnIndex, isFixed) {
                            if (!utils.selectable($grid))
                                return;
                            $grid.igGridSelection("selectCell", rowIndex, columnIndex, utils.fixable($grid) ? isFixed : undefined);
                            var ui = { owner: $grid.data("igGridSelection"),
                                selectedCells: $grid.igGridSelection("selectedCells") };
                            var selectedCells = $grid.igGridSelection("selectedCells");
                            if (selectedCells.length > 0)
                                ui.cell = selectedCells[0];
                            selectCellChange({ target: $grid[0] }, ui);
                            var selectedCell = getSelectedCell($grid);
                            var $element = $(selectedCell.element);
                            var ntsCombo = $element.find(".nts-combo-container");
                            if (ntsCombo.length > 0) {
                                ntsCombo.find("input").select();
                            }
                            var ntsSwitchs = $element.find(".nts-switch-container");
                            if (ntsSwitchs.length > 0) {
                                ntsSwitchs.find("button").filter(function (i, b) { return $(b).hasClass("selected"); }).focus();
                            }
                        }
                        selection_1.selectCell = selectCell;
                        function selectCellById($grid, rowId, columnKey) {
                            return;
                        }
                        selection_1.selectCellById = selectCellById;
                        function selectCellChange(evt, ui) {
                            if (uk.util.isNullOrUndefined(ui.cell))
                                return;
                            $(evt.target).data(internal.SELECTED_CELL, ui.cell);
                        }
                        function onCellNavigate(evt, enterDirection) {
                            var grid = evt.currentTarget;
                            var $targetGrid = fixedColumns.realGridOf($(grid));
                            if (utils.isTabKey(evt)) {
                                if (utils.isEditMode($targetGrid))
                                    $targetGrid.igGridUpdating("endEdit");
                                if (evt.shiftKey) {
                                    selection.selectPrev($targetGrid);
                                }
                                else {
                                    selection.selectFollow($targetGrid);
                                }
                                evt.preventDefault();
                                return;
                            }
                            if (utils.isEnterKey(evt)) {
                                if (evt.shiftKey) {
                                    selection.selectBefore($targetGrid, enterDirection);
                                }
                                else {
                                    selection.selectFollow($targetGrid, enterDirection);
                                }
                                evt.stopImmediatePropagation();
                                return;
                            }
                        }
                        selection_1.onCellNavigate = onCellNavigate;
                        function clearSelection($grid) {
                            if (utils.selectable($grid)) {
                                $grid.igGridSelection("clearSelection");
                                return;
                            }
                            var $targetGrid = fixedColumns.realGridOf($grid);
                            if (!uk.util.isNullOrUndefined($targetGrid) && utils.selectable($targetGrid))
                                $targetGrid.igGridSelection("clearSelection");
                        }
                        var Direction = (function () {
                            function Direction() {
                            }
                            Direction.prototype.bind = function (evt) {
                                onCellNavigate(evt, this.to);
                            };
                            return Direction;
                        }());
                        selection_1.Direction = Direction;
                    })(selection || (selection = {}));
                    var columnSize;
                    (function (columnSize) {
                        function init($grid, columns) {
                            initValueExists($grid).done(function (res) {
                                if (res)
                                    return;
                                var columnWidths = {};
                                _.forEach(columns, function (col, index) {
                                    flat(col, columnWidths);
                                });
                                saveAll($grid, columnWidths);
                            });
                        }
                        columnSize.init = init;
                        function flat(col, columnWidths) {
                            if (col.group) {
                                _.forEach(col.group, function (sCol) {
                                    flat(sCol, columnWidths);
                                });
                                return;
                            }
                            columnWidths[col.key] = parseInt(col.width);
                        }
                        function exists($grid) {
                            return uk.localStorage.getItem(getStorageKey($grid)).isPresent();
                        }
                        columnSize.exists = exists;
                        function createResizeOptions(key) {
                            var resizing = { name: feature.RESIZING };
                            resizing.columnSettings = [{ columnKey: key, allowResizing: false, minimumWidth: 0 }];
                            return resizing;
                        }
                        columnSize.createResizeOptions = createResizeOptions;
                        function load($grid) {
                            var storeKey = getStorageKey($grid);
                            storage.getItem(storeKey).done(function (widths) {
                                widths.ifPresent(function (columns) {
                                    var widthColumns;
                                    try {
                                        widthColumns = JSON.parse(columns);
                                    }
                                    catch (e) {
                                        widthColumns = columns;
                                    }
                                    setWidths($grid, widthColumns);
                                    $grid.closest(".nts-grid-container").css("visibility", "visible");
                                    return null;
                                });
                            });
                        }
                        columnSize.load = load;
                        function save($grid, columnKey, columnWidth) {
                            var storeKey = getStorageKey($grid);
                            if (storage instanceof dist.Local) {
                                var columnsWidth = uk.localStorage.getItem(storeKey);
                                var widths = {};
                                if (columnsWidth.isPresent()) {
                                    widths = JSON.parse(columnsWidth.get());
                                    widths[columnKey] = columnWidth;
                                }
                                else {
                                    widths[columnKey] = columnWidth;
                                }
                                uk.localStorage.setItemAsJson(storeKey, widths);
                            }
                            else if (storage instanceof dist.Remote) {
                                var width = {};
                                width[columnKey] = columnWidth;
                                storage.setItemAsJson(storeKey, width);
                            }
                        }
                        columnSize.save = save;
                        function saveAll($grid, widths) {
                            var storeKey = getStorageKey($grid);
                            storage.getItem(storeKey).done(function (columnWidths) {
                                if (!columnWidths.isPresent()) {
                                    storage.setItemAsJson(storeKey, widths);
                                }
                            });
                        }
                        function initValueExists($grid) {
                            var dfd = $.Deferred();
                            var storeKey = getStorageKey($grid);
                            storage.getItem(storeKey).done(function (columnWidths) {
                                dfd.resolve(columnWidths.isPresent());
                            });
                            return dfd.promise();
                        }
                        function getStorageKey($grid) {
                            return uk.request.location.current.rawUrl + "/" + $grid.attr("id");
                        }
                        function loadOne($grid, columnKey) {
                            var storeKey = getStorageKey($grid);
                            storage.getItem(storeKey).done(function (widths) {
                                widths.ifPresent(function (columns) {
                                    var widthColumns = JSON.parse(columns);
                                    setWidth($grid, columnKey, widthColumns[columnKey]);
                                    return null;
                                });
                            });
                        }
                        columnSize.loadOne = loadOne;
                        function loadFixedColumns($grid) {
                            var storeKey = getStorageKey($grid);
                            uk.localStorage.getItem(storeKey).ifPresent(function (columns) {
                                var fixedColumns = utils.getVisibleFixedColumns($grid);
                                if (uk.util.isNullOrUndefined(fixedColumns) || fixedColumns.length === 0)
                                    return;
                                var widthColumns = JSON.parse(columns);
                                _.forEach(fixedColumns, function (fixedCol) {
                                    setWidth($grid, fixedCol.key, widthColumns[fixedCol.key]);
                                });
                                return null;
                            });
                        }
                        columnSize.loadFixedColumns = loadFixedColumns;
                        function setWidth($grid, columnKey, width, noCheck) {
                            if (noCheck !== true && uk.util.isNullOrUndefined($grid.data("igGridResizing")))
                                return;
                            try {
                                $grid.igGridResizing("resize", columnKey, width);
                            }
                            catch (e) { }
                        }
                        function setWidths($grid, columns) {
                            if (uk.util.isNullOrUndefined($grid.data("igGridResizing"))
                                || uk.util.isNullOrUndefined(columns))
                                return;
                            var columnKeys = Object.keys(columns);
                            _.forEach(columnKeys, function (key, index) {
                                setWidth($grid, key, columns[key], true);
                            });
                        }
                    })(columnSize || (columnSize = {}));
                    var functions;
                    (function (functions) {
                        functions.ERRORS = "errors";
                        functions.UPDATE_ROW = "updateRow";
                        functions.SET_STATE = "setState";
                        functions.UPDATED_CELLS = "updatedCells";
                        functions.ENABLE_CONTROL = "enableNtsControlAt";
                        functions.ENABLE_ALL_CONTROLS = "enableNtsControls";
                        functions.DISABLE_CONTROL = "disableNtsControlAt";
                        functions.DISABLE_ALL_CONTROLS = "disableNtsControls";
                        functions.DIRECT_ENTER = "directEnter";
                        functions.CHECK_ALL = "checkAll";
                        functions.UNCHECK_ALL = "uncheckAll";
                        functions.HEADER_TEXT = "headerText";
                        functions.SELECTED_SHEET = "selectedSheet";
                        functions.CLEAR_ROW_STATES = "clearRowStates";
                        functions.RESET_ORIG_DS = "resetOrigDataSource";
                        functions.DESTROY = "destroy";
                        function ntsAction($grid, method, params) {
                            switch (method) {
                                case functions.UPDATE_ROW:
                                    var autoCommit = $grid.data("igGrid") !== null && $grid.igGrid("option", "autoCommit") ? true : false;
                                    updateRow($grid, params[0], params[1], autoCommit);
                                    break;
                                case functions.SET_STATE:
                                    setState($grid, params[0], params[1], params[2]);
                                    break;
                                case functions.ENABLE_CONTROL:
                                    enableNtsControlAt($grid, params[0], params[1], params[2]);
                                    break;
                                case functions.ENABLE_ALL_CONTROLS:
                                    enableNtsControls($grid, params[0], params[1], params[2]);
                                    break;
                                case functions.DISABLE_CONTROL:
                                    disableNtsControlAt($grid, params[0], params[1], params[2]);
                                    break;
                                case functions.DISABLE_ALL_CONTROLS:
                                    disableNtsControls($grid, params[0], params[1], params[2]);
                                    break;
                                case functions.DIRECT_ENTER:
                                    var direction = $grid.data(internal.ENTER_DIRECT);
                                    direction.to = params[0];
                                    if (utils.fixable($grid)) {
                                        var fixedTable = fixedColumns.getFixedTable($grid);
                                        if (!uk.util.isNullOrUndefined(fixedTable)) {
                                            fixedTable.data(internal.ENTER_DIRECT).to = params[0];
                                        }
                                    }
                                    break;
                                case functions.CHECK_ALL:
                                    checkAll($grid, params[0]);
                                    break;
                                case functions.UNCHECK_ALL:
                                    uncheckAll($grid, params[0]);
                                    break;
                                case functions.HEADER_TEXT:
                                    setHeaderText($grid, params[0], params[1], params[2]);
                                    break;
                                case functions.CLEAR_ROW_STATES:
                                    clearStates($grid, params[0]);
                                    break;
                                case functions.RESET_ORIG_DS:
                                    resetOrigDs($grid, params[0]);
                                    break;
                                case functions.DESTROY:
                                    destroy($grid);
                                    break;
                                case functions.SELECTED_SHEET:
                                    return getSelectedSheet($grid);
                                case functions.UPDATED_CELLS:
                                    return $grid.data(internal.UPDATED_CELLS);
                                case functions.ERRORS:
                                    return getErrors($grid);
                            }
                        }
                        functions.ntsAction = ntsAction;
                        function getErrors($grid) {
                            if (!$grid)
                                return [];
                            return $grid.data(internal.ERRORS);
                        }
                        function updateRow($grid, rowId, object, autoCommit) {
                            var selectedSheet = getSelectedSheet($grid);
                            if (selectedSheet) {
                                var grid_1 = $grid.data("igGrid");
                                var options_2 = grid_1.options;
                                Object.keys(object).forEach(function (k) {
                                    if (!_.includes(selectedSheet.columns, k)) {
                                        grid_1.dataSource.setCellValue(rowId, k, object[k], grid_1.options.autoCommit);
                                        delete object[k];
                                        if (!uk.util.isNullOrUndefined(options_2.userId) && _.isFunction(options_2.getUserId)) {
                                            var uId = options_2.getUserId(rowId);
                                            if (uId === options_2.userId) {
                                                var targetEdits = $grid.data(internal.TARGET_EDITS);
                                                if (!targetEdits) {
                                                    targetEdits = {};
                                                    targetEdits[rowId] = [k];
                                                    $grid.data(internal.TARGET_EDITS, targetEdits);
                                                    return;
                                                }
                                                if (!targetEdits[rowId]) {
                                                    targetEdits[rowId] = [k];
                                                    return;
                                                }
                                                targetEdits[rowId].push(k);
                                            }
                                            else {
                                                var otherEdits = $grid.data(internal.OTHER_EDITS);
                                                if (!otherEdits) {
                                                    otherEdits = {};
                                                    otherEdits[rowId] = [k];
                                                    $grid.data(internal.OTHER_EDITS, otherEdits);
                                                    return;
                                                }
                                                if (!otherEdits[rowId]) {
                                                    otherEdits[rowId] = [k];
                                                    return;
                                                }
                                                otherEdits[rowId].push(k);
                                            }
                                        }
                                    }
                                });
                            }
                            updating.updateRow($grid, rowId, object, undefined, true);
                            if (!autoCommit) {
                                var updatedRow = $grid.igGrid("rowById", rowId, false);
                                $grid.igGrid("commit");
                                if (updatedRow !== undefined)
                                    $grid.igGrid("virtualScrollTo", $(updatedRow).data("row-idx"));
                            }
                        }
                        function setState($grid, rowId, key, states) {
                            var cellFormatter = $grid.data(internal.CELL_FORMATTER);
                            var cellStateFeatureDef = cellFormatter.cellStateFeatureDef;
                            if (cellFormatter.rowStates) {
                                var row = cellFormatter.rowStates[rowId];
                                if (row) {
                                    var sts = row[key];
                                    if (sts) {
                                        if (sts[0][cellStateFeatureDef.state]) {
                                            sts[0][cellStateFeatureDef.state] = states;
                                        }
                                    }
                                    else {
                                        var cellState = {};
                                        cellState[cellStateFeatureDef.rowId] = rowId;
                                        cellState[cellStateFeatureDef.columnKey] = key;
                                        cellState[cellStateFeatureDef.state] = states;
                                        row[key] = [cellState];
                                    }
                                }
                                else {
                                    cellFormatter.rowStates[rowId] = {};
                                    var cellState = {};
                                    cellState[cellStateFeatureDef.rowId] = rowId;
                                    cellState[cellStateFeatureDef.columnKey] = key;
                                    cellState[cellStateFeatureDef.state] = states;
                                    cellFormatter.rowStates[rowId][key] = [cellState];
                                }
                            }
                            else {
                                cellFormatter.rowStates = {};
                                var cellState = {};
                                cellState[cellStateFeatureDef.rowId] = rowId;
                                cellState[cellStateFeatureDef.columnKey] = key;
                                cellState[cellStateFeatureDef.state] = states;
                                var colState = {};
                                colState[key] = [cellState];
                                cellFormatter.rowStates[rowId] = colState;
                            }
                            var selectedSheet = getSelectedSheet($grid);
                            if (selectedSheet && !_.includes(selectedSheet.columns, key)) {
                                var options = $grid.data(internal.GRID_OPTIONS);
                                var stateFt = feature.find(options.ntsFeatures, feature.CELL_STATE);
                                if (stateFt) {
                                    var newState = {};
                                    newState[stateFt.rowId] = rowId;
                                    newState[stateFt.columnKey] = key;
                                    newState[stateFt.state] = states;
                                    stateFt.states.push(newState);
                                }
                                return;
                            }
                            updating.renderCell($grid, rowId, key, undefined, true);
                        }
                        function disableNtsControls($grid, columnKey, controlType, header) {
                            var ds = $grid.igGrid("option", "dataSource");
                            var primaryKey = $grid.igGrid("option", "primaryKey");
                            if (header && controlType === ntsControls.CHECKBOX) {
                                var setting = $grid.data(internal.SETTINGS);
                                if (setting && setting.descriptor && setting.descriptor.colIdxes) {
                                    var key = setting.descriptor.colIdxes[columnKey];
                                    var cellElm = void 0;
                                    var headerCells = setting.descriptor.headerCells;
                                    if (!headerCells) {
                                        cellElm = setting.descriptor.headerParent.find("th").filter(function () {
                                            var c = $(this);
                                            var id = c.attr("id");
                                            if (!id)
                                                return false;
                                            var parts = id.split("_");
                                            id = parts[parts.length - 1];
                                            return c.css("display") !== "none" && id === columnKey;
                                        });
                                    }
                                    else {
                                        var cells = headerCells.filter(function (c) {
                                            return c.css("display") !== "none";
                                        });
                                        cellElm = cells[key];
                                    }
                                    if (cellElm) {
                                        var control = ntsControls.getControl(controlType);
                                        if (control) {
                                            control.disable(cellElm);
                                        }
                                    }
                                }
                            }
                            for (var i = 0; i < ds.length; i++) {
                                var id = ds[i][primaryKey];
                                disableNtsControlAt($grid, id, columnKey, controlType);
                                color.pushDisable($grid, { id: id, columnKey: columnKey });
                            }
                        }
                        function enableNtsControls($grid, columnKey, controlType) {
                            var ds = $grid.igGrid("option", "dataSource");
                            var primaryKey = $grid.igGrid("option", "primaryKey");
                            if (header && controlType === ntsControls.CHECKBOX) {
                                var setting = $grid.data(internal.SETTINGS);
                                if (setting && setting.descriptor && setting.descriptor.colIdxes) {
                                    var key = setting.descriptor.colIdxes[columnKey];
                                    var cellElm = void 0;
                                    var headerCells = setting.descriptor.headerCells;
                                    if (!headerCells) {
                                        cellElm = setting.descriptor.headerParent.find("th").filter(function () {
                                            var c = $(this);
                                            var id = c.attr("id");
                                            if (!id)
                                                return false;
                                            var parts = id.split("_");
                                            id = parts[parts.length - 1];
                                            return c.css("display") !== "none" && id === columnKey;
                                        });
                                    }
                                    else {
                                        var cells = headerCells.filter(function (c) {
                                            return c.css("display") !== "none";
                                        });
                                        cellElm = cells[key];
                                    }
                                    if (cellElm) {
                                        var control = ntsControls.getControl(controlType);
                                        if (control) {
                                            control.enable(cellElm);
                                        }
                                    }
                                }
                            }
                            for (var i = 0; i < ds.length; i++) {
                                var id = ds[i][primaryKey];
                                enableNtsControlAt($grid, id, columnKey, controlType);
                                color.popDisable($grid, { id: id, columnKey: columnKey });
                            }
                        }
                        function disableNtsControlAt($grid, rowId, columnKey, controlType) {
                            var cellContainer = $grid.igGrid("cellById", rowId, columnKey);
                            var control = ntsControls.getControl(controlType);
                            if (uk.util.isNullOrUndefined(control))
                                return;
                            var $cellContainer = $(cellContainer);
                            control.disable($cellContainer);
                            if (!$cellContainer.hasClass(color.Disable))
                                $cellContainer.addClass(color.Disable);
                            color.pushDisable($grid, { id: rowId, columnKey: columnKey });
                        }
                        function enableNtsControlAt($grid, rowId, columnKey, controlType) {
                            var cellContainer = $grid.igGrid("cellById", rowId, columnKey);
                            var control = ntsControls.getControl(controlType);
                            if (uk.util.isNullOrUndefined(control))
                                return;
                            var $cellContainer = $(cellContainer);
                            control.enable($cellContainer);
                            $cellContainer.removeClass(color.Disable);
                            color.popDisable($grid, { id: rowId, columnKey: columnKey });
                        }
                        function checkAll($grid, key) {
                            var ds = $grid.igGrid("option", "dataSource");
                            var primaryKey = $grid.igGrid("option", "primaryKey");
                            if (utils.getControlType($grid, key) !== ntsControls.CHECKBOX)
                                return;
                            var cbSelect = $grid.data(internal.CB_SELECTED);
                            var colCb = cbSelect[key];
                            for (var i = 0; i < ds.length; i++) {
                                var id = ds[i][primaryKey];
                                if (colCb && colCb.disableRows
                                    && colCb.disableRows.has(id))
                                    continue;
                                updating.updateCell($grid, id, key, true, undefined, true);
                            }
                        }
                        function uncheckAll($grid, key) {
                            var ds = $grid.igGrid("option", "dataSource");
                            var primaryKey = $grid.igGrid("option", "primaryKey");
                            if (utils.getControlType($grid, key) !== ntsControls.CHECKBOX)
                                return;
                            var cbSelect = $grid.data(internal.CB_SELECTED);
                            var colCb = cbSelect[key];
                            for (var i = 0; i < ds.length; i++) {
                                var id = ds[i][primaryKey];
                                if (colCb && colCb.disableRows && colCb.disableRows.has(id))
                                    continue;
                                updating.updateCell($grid, id, key, false, undefined, true);
                            }
                        }
                        function setHeaderText($grid, key, text, group) {
                            if (!group) {
                                var setting = $grid.data(internal.SETTINGS);
                                if (!setting || !setting.descriptor || !setting.descriptor.colIdxes
                                    || !setting.descriptor.headerCells)
                                    return;
                                var colIdx = setting.descriptor.colIdxes[key];
                                var fixedColsLen = setting.descriptor.headerCells.length - Object.keys(setting.descriptor.colIdxes).length;
                                var headerCell = setting.descriptor.headerCells[colIdx + fixedColsLen];
                                if (headerCell) {
                                    $(headerCell.find("span")[1]).html(text);
                                }
                                var options_3 = $grid.data(internal.GRID_OPTIONS);
                                updateHeaderColumn(options_3.columns, key, text, group);
                                var sheetMng_1 = $grid.data(internal.SHEETS);
                                if (sheetMng_1) {
                                    Object.keys(sheetMng_1.sheetColumns).forEach(function (k) {
                                        updateHeaderColumn(sheetMng_1.sheetColumns[k], key, text, group);
                                    });
                                }
                                return;
                            }
                            var headersTable = $grid.igGrid("headersTable");
                            headersTable.find("th").each(function () {
                                var $self = $(this);
                                var colspan = $self.attr("colspan");
                                if (uk.util.isNullOrUndefined(colspan))
                                    return;
                                var label = $self.attr("aria-label");
                                if (key === label.trim()) {
                                    $self.attr("aria-label", text);
                                    $self.children("span.ui-iggrid-headertext").text(text);
                                    return false;
                                }
                            });
                            var options = $grid.data(internal.GRID_OPTIONS);
                            updateHeaderColumn(options.columns, key, text, group);
                            var sheetMng = $grid.data(internal.SHEETS);
                            if (sheetMng) {
                                Object.keys(sheetMng.sheetColumns).forEach(function (k) {
                                    updateHeaderColumn(sheetMng.sheetColumns[k], key, text, group);
                                });
                            }
                        }
                        function updateHeaderColumn(columns, key, text, group) {
                            var updated = false;
                            _.forEach(columns, function (c, i) {
                                if (group && c.group && c.headerText === key) {
                                    updated = true;
                                    c.headerText = text;
                                    return false;
                                }
                                if (!group && c.group) {
                                    updated = updateHeaderColumn(c.group, key, text, group);
                                    if (updated)
                                        return false;
                                }
                                if (!group && !c.group && c.key === key) {
                                    updated = true;
                                    c.headerText = text;
                                    return false;
                                }
                            });
                            return updated;
                        }
                        function clearStates($grid, id) {
                            var cellFormatter = $grid.data(internal.CELL_FORMATTER);
                            if (cellFormatter && cellFormatter.rowStates && cellFormatter.rowStates[id]) {
                                delete cellFormatter.rowStates[id];
                                var $row = $grid.igGrid("rowById", id, false);
                                var $cells_1 = $row.find("td");
                                [color.Error, color.Alarm, color.ManualEditTarget, color.ManualEditOther,
                                    color.Reflect, color.Calculation, color.Disable].forEach(function (s) {
                                    if ($cells_1.hasClass(s))
                                        $cells_1.removeClass(s);
                                });
                            }
                        }
                        function resetOrigDs($grid, ds) {
                            $grid.data(internal.ORIG_DS, ds);
                            $grid.data(internal.UPDATED_CELLS, null);
                        }
                        function getSelectedSheet($grid) {
                            var sheet = $grid.data(internal.SHEETS);
                            if (!sheet || !sheet.currentSheet)
                                return;
                            return _.find(sheet.sheets, function (s) {
                                return s.name === sheet.currentSheet;
                            });
                        }
                        function destroy($grid) {
                            var $container = $grid.closest(".nts-grid-container");
                            if ($container.length === 0) {
                                $grid.igGrid("destroy");
                                $grid.off();
                                $grid.removeData();
                                return;
                            }
                            $container.find(".nts-grid-sheet-buttons").remove();
                            $($grid.igGrid("container")).unwrap().unwrap();
                            $grid.igGrid("destroy");
                            $grid.off();
                            $grid.removeData();
                        }
                    })(functions || (functions = {}));
                    var ntsControls;
                    (function (ntsControls) {
                        ntsControls.LABEL = 'Label';
                        ntsControls.LINK_LABEL = 'LinkLabel';
                        ntsControls.CHECKBOX = 'CheckBox';
                        ntsControls.SWITCH_BUTTONS = 'SwitchButtons';
                        ntsControls.COMBOBOX = 'ComboBox';
                        ntsControls.BUTTON = 'Button';
                        ntsControls.DELETE_BUTTON = 'DeleteButton';
                        ntsControls.TEXTBOX = 'TextBox';
                        ntsControls.TEXT_EDITOR = 'TextEditor';
                        ntsControls.FLEX_IMAGE = 'FlexImage';
                        ntsControls.IMAGE = 'Image';
                        ntsControls.HEIGHT_CONTROL = "27px";
                        ntsControls.COMBO_CLASS = "nts-combo-container";
                        function getControl(name) {
                            switch (name) {
                                case ntsControls.CHECKBOX:
                                    return new CheckBox();
                                case ntsControls.SWITCH_BUTTONS:
                                    return new SwitchButtons();
                                case ntsControls.COMBOBOX:
                                    return new ComboBox();
                                case ntsControls.BUTTON:
                                    return new Button();
                                case ntsControls.DELETE_BUTTON:
                                    return new DeleteButton();
                                case ntsControls.TEXT_EDITOR:
                                    return new TextEditor();
                                case ntsControls.LINK_LABEL:
                                    return new LinkLabel();
                                case ntsControls.FLEX_IMAGE:
                                    return new FlexImage();
                                case ntsControls.IMAGE:
                                    return new Image();
                            }
                        }
                        ntsControls.getControl = getControl;
                        function drawLabel($grid, column, cellFormatter) {
                            column.formatter = function (value, rowObj) {
                                if (uk.util.isNullOrUndefined(rowObj))
                                    return value;
                                var $self = this;
                                var rowId = rowObj[$grid.igGrid("option", "primaryKey")];
                                var controlCls = "nts-grid-control-" + column.key + "-" + rowId;
                                var $container = $("<div/>").append($("<div/>").addClass(controlCls).css("height", ntsControls.HEIGHT_CONTROL));
                                setTimeout(function () {
                                    var rId = rowObj[$grid.igGrid("option", "primaryKey")];
                                    var $gridCell = internal.getCellById($grid, rId, column.key);
                                    if ($gridCell && $($gridCell.children()[0]).children().length === 0) {
                                        var action = void 0;
                                        if (column.click && _.isFunction(column.click)) {
                                            action = function () { return column.click(rowId, column.key); };
                                        }
                                        $("." + controlCls).append(new Label(action).draw({ text: value }));
                                        var cellElement = {
                                            id: rId,
                                            columnKey: column.key,
                                            $element: $gridCell
                                        };
                                        cellFormatter.style($grid, cellElement);
                                        cellFormatter.setTextColor($grid, cellElement);
                                        cellFormatter.setTextStyle($grid, cellElement);
                                    }
                                }, 0);
                                return $container.html();
                            };
                        }
                        ntsControls.drawLabel = drawLabel;
                        function createHeaderCheckbox(data, key) {
                            var defaultOptions = {
                                update: $.noop,
                                initValue: false,
                                enable: true
                            };
                            var options = $.extend({}, defaultOptions, data);
                            return new CheckBox().draw(options).addClass("nts-grid-header-control-" + key).prop("outerHTML");
                        }
                        ntsControls.createHeaderCheckbox = createHeaderCheckbox;
                        function bindCbHeaderColumns(options, columns, selectionColumns) {
                            options.headerCellRendered = function (evt, ui) {
                                var $grid = $(ui.owner.element);
                                var column = _.remove(columns, function (c) { return c === ui.columnKey; });
                                if (!column || column.length === 0)
                                    return;
                                var columnConf = selectionColumns[column[0]];
                                if (columnConf) {
                                    selectionColumns[column[0]].th = ui.th;
                                }
                                $(ui.th).find(".nts-grid-header-control-" + column[0]).find("input[type='checkbox']")
                                    .on("change", function () {
                                    var $cb = $(this);
                                    var selected = $cb.is(":checked");
                                    var cbSelectCols = $grid.data(internal.CB_SELECTED);
                                    var cbSelectConf = cbSelectCols[column[0]];
                                    if (!cbSelectConf)
                                        return;
                                    _.forEach(options.dataSource, function (r) {
                                        if (!r)
                                            return;
                                        var id = r[options.primaryKey];
                                        if (cbSelectConf && (cbSelectConf.hiddenRows
                                            && cbSelectConf.hiddenRows.some(function (v) { return v === id; }))
                                            || (cbSelectConf.disableRows
                                                && cbSelectConf.disableRows.has(id)))
                                            return;
                                        updating.updateCell($grid, id, ui.columnKey, selected, undefined, true);
                                    });
                                    cbSelectConf.selectAll = selected;
                                    if (selected) {
                                        var hiddenCount = cbSelectConf.hiddenRows ? cbSelectConf.hiddenRows.length : 0;
                                        var disableCount = cbSelectConf.disableRows ? cbSelectConf.disableRows.size : 0;
                                        cbSelectConf.quantity = options.dataSource.length - hiddenCount - disableCount;
                                        return;
                                    }
                                    cbSelectConf.quantity = 0;
                                });
                            };
                        }
                        ntsControls.bindCbHeaderColumns = bindCbHeaderColumns;
                        var NtsControlBase = (function () {
                            function NtsControlBase() {
                                this.readOnly = false;
                            }
                            return NtsControlBase;
                        }());
                        var CheckBox = (function (_super) {
                            __extends(CheckBox, _super);
                            function CheckBox() {
                                _super.apply(this, arguments);
                            }
                            CheckBox.prototype.containerClass = function () {
                                return "nts-checkbox-container";
                            };
                            CheckBox.prototype.draw = function (data) {
                                var checkBoxText;
                                var setChecked = data.update;
                                var initValue = data.initValue;
                                var $wrapper = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                                $wrapper.addClass("ntsControl").on("click", function (e) {
                                    if ($wrapper.data("readonly") === true)
                                        e.preventDefault();
                                });
                                var text = data.controlDef.options[data.controlDef.optionsText];
                                if (text) {
                                    checkBoxText = text;
                                }
                                else {
                                    checkBoxText = $wrapper.text();
                                    $wrapper.text('');
                                }
                                var $checkBoxLabel = $("<label class='ntsCheckBox'></label>");
                                var $checkBox = $('<input type="checkbox">').on("change", function () {
                                    setChecked($(this).is(":checked"));
                                }).appendTo($checkBoxLabel);
                                var $box = $("<span class='box'></span>").appendTo($checkBoxLabel);
                                if (checkBoxText && checkBoxText.length > 0)
                                    var label = $("<span class='label'></span>").html(checkBoxText).appendTo($checkBoxLabel);
                                $checkBoxLabel.appendTo($wrapper);
                                var checked = initValue !== undefined ? initValue : true;
                                $wrapper.data("readonly", this.readOnly);
                                var $checkBox = $wrapper.find("input[type='checkbox']");
                                if (checked === true)
                                    $checkBox.attr("checked", "checked");
                                else
                                    $checkBox.removeAttr("checked");
                                if (data.enable === true)
                                    $checkBox.removeAttr("disabled");
                                else
                                    $checkBox.attr("disabled", "disabled");
                                return $wrapper;
                            };
                            CheckBox.prototype.disable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                                $wrapper.find("input[type='checkbox']").attr("disabled", "disabled");
                            };
                            CheckBox.prototype.enable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                                $wrapper.find("input[type='checkbox']").removeAttr("disabled");
                            };
                            return CheckBox;
                        }(NtsControlBase));
                        var SwitchButtons = (function (_super) {
                            __extends(SwitchButtons, _super);
                            function SwitchButtons() {
                                _super.apply(this, arguments);
                            }
                            SwitchButtons.prototype.containerClass = function () {
                                return "nts-switch-container";
                            };
                            SwitchButtons.prototype.draw = function (data) {
                                var selectedCssClass = 'selected';
                                var options = data.controlDef.options;
                                var optionsValue = data.controlDef.optionsValue;
                                var optionsText = data.controlDef.optionsText;
                                var selectedValue = data.initValue;
                                var container = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                                container.on(events.Handler.KEY_UP, function (evt) {
                                    var $buttons = container.find("button");
                                    var index;
                                    $buttons.each(function (i, elm) {
                                        if (elm === document.activeElement) {
                                            index = i;
                                            return false;
                                        }
                                    });
                                    if (!uk.util.isNullOrUndefined(index)) {
                                        var arrowNav = false;
                                        if (utils.isArrowLeft(evt)) {
                                            index = index === 0 ? ($buttons.length - 1) : --index;
                                            arrowNav = true;
                                        }
                                        if (utils.isArrowRight(evt)) {
                                            index = index === $buttons.length - 1 ? 0 : ++index;
                                            arrowNav = true;
                                        }
                                        var $targetButton = $buttons.eq(index);
                                        $targetButton.focus();
                                        if (arrowNav) {
                                            var selectedValue = $targetButton.data('swbtn');
                                            $('button', container).removeClass(selectedCssClass);
                                            $targetButton.addClass(selectedCssClass);
                                            data.update(selectedValue);
                                        }
                                    }
                                });
                                var distinction = data.controlDef.distinction;
                                var switchOptions;
                                if (distinction && (switchOptions = distinction[data.rowId])) {
                                    switchOptions = options.filter(function (o) {
                                        return switchOptions.indexOf(o.value) > -1;
                                    });
                                }
                                else {
                                    switchOptions = options;
                                }
                                _.forEach(switchOptions, function (opt) {
                                    var value = opt[optionsValue];
                                    var text = opt[optionsText];
                                    var btn = $('<button>').text(text).css("height", "26px")
                                        .addClass('nts-switch-button')
                                        .attr('data-swbtn', value)
                                        .attr('tabindex', -1)
                                        .on('click', function () {
                                        var selectedValue = $(this).data('swbtn');
                                        $('button', container).removeClass(selectedCssClass);
                                        $(this).addClass(selectedCssClass);
                                        data.update(selectedValue);
                                    });
                                    if (value === selectedValue) {
                                        btn.addClass(selectedCssClass);
                                    }
                                    container.append(btn);
                                });
                                (data.enable === true) ? $('button', container).prop("disabled", false)
                                    : $('button', container).prop("disabled", true);
                                return container;
                            };
                            SwitchButtons.prototype.enable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                                $('button', $wrapper).prop("disabled", false);
                            };
                            SwitchButtons.prototype.disable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                                $('button', $wrapper).prop("disabled", true);
                            };
                            return SwitchButtons;
                        }(NtsControlBase));
                        var ComboBox = (function (_super) {
                            __extends(ComboBox, _super);
                            function ComboBox() {
                                _super.apply(this, arguments);
                            }
                            ComboBox.prototype.containerClass = function () {
                                return "nts-combo-container";
                            };
                            ComboBox.prototype.draw = function (data) {
                                var self = this;
                                var distanceColumns = data.controlDef.spaceSize === "small" ? '  ' : '     ';
                                var fillCharacter = ' ';
                                var maxWidthCharacter = 15;
                                var container = $("<div/>").addClass(this.containerClass()).data("enable", data.enable);
                                var columns = data.controlDef.columns;
                                var itemTemplate = undefined;
                                var haveColumn = columns && columns.length > 0;
                                if (haveColumn) {
                                    itemTemplate = '<div class="nts-combo-item">';
                                    _.forEach(columns, function (item, i) {
                                        itemTemplate += '<div class="nts-column nts-combo-column-' + i + '">${' + item.prop + '}</div>';
                                    });
                                    itemTemplate += '</div>';
                                }
                                if (data.controlDef.displayMode === "codeName") {
                                    data.controlDef.options = data.controlDef.options.map(function (option) {
                                        var newOptionText = '';
                                        if (haveColumn) {
                                            _.forEach(columns, function (item, i) {
                                                var prop = option[item.prop];
                                                var length = item.length;
                                                if (i === columns.length - 1) {
                                                    newOptionText += prop;
                                                }
                                                else {
                                                    newOptionText += uk.text.padRight(prop, fillCharacter, length) + distanceColumns;
                                                }
                                            });
                                        }
                                        else {
                                            newOptionText = option[data.controlDef.optionsText];
                                        }
                                        option['nts-combo-label'] = newOptionText;
                                        return option;
                                    });
                                }
                                var comboMode = data.controlDef.editable ? 'editable' : 'dropdown';
                                container.igCombo({
                                    dataSource: data.controlDef.options,
                                    valueKey: data.controlDef.optionsValue,
                                    textKey: data.controlDef.displayMode === 'codeName'
                                        ? 'nts-combo-label' : data.controlDef.optionsText,
                                    mode: comboMode,
                                    disabled: !data.enable,
                                    placeHolder: '',
                                    enableClearButton: false,
                                    initialSelectedItems: [
                                        { value: data.initValue }
                                    ],
                                    itemTemplate: itemTemplate,
                                    selectionChanged: function (evt, ui) {
                                        var _self = self;
                                        if (ui.items.length > 0) {
                                            var selectedValue_1 = ui.items[0].data[data.controlDef.optionsValue];
                                            data.update(selectedValue_1);
                                            setTimeout(function () {
                                                var __self = _self;
                                                var $gridControl = $(evt.target).closest("div[class*=nts-grid-control]");
                                                if (uk.util.isNullOrUndefined($gridControl))
                                                    return;
                                                var cls = $gridControl.attr("class");
                                                var classNameParts = cls.split("-");
                                                var rowId = classNameParts.pop();
                                                var columnKey = classNameParts.pop();
                                                var targetCell = internal.getCellById(__self.$containedGrid, rowId, columnKey);
                                                if (!targetCell)
                                                    return;
                                                var $comboContainer = targetCell.find("." + __self.containerClass());
                                                $comboContainer.data(internal.COMBO_SELECTED, selectedValue_1);
                                                if (data.bounce) {
                                                    updating.updateCell(__self.$containedGrid, rowId, data.bounce, selectedValue_1);
                                                }
                                            }, 0);
                                        }
                                    },
                                    rendered: function () {
                                        var tabIndex = !uk.util.isNullOrUndefined(data.tabIndex) ? data.tabIndex : -1;
                                        container.igCombo("option", "tabIndex", tabIndex);
                                    }
                                });
                                container.data(internal.COMBO_SELECTED, data.initValue);
                                if (haveColumn) {
                                    var totalWidth = 0;
                                    var $dropDownOptions = container.igCombo("dropDown");
                                    _.forEach(columns, function (item, i) {
                                        var charLength = item.length;
                                        var width = charLength * maxWidthCharacter + 10;
                                        var $comboCol = $dropDownOptions.find('.nts-combo-column-' + i);
                                        $comboCol.width(width);
                                        if (i !== columns.length - 1) {
                                            $comboCol.css("float", "left");
                                        }
                                        totalWidth += width + 10;
                                    });
                                    $dropDownOptions.find(".nts-combo-item").css({ minWidth: totalWidth });
                                    container.css({ minWidth: totalWidth });
                                }
                                if (!uk.util.isNullOrUndefined(data.controlDef.width)) {
                                    container.igCombo("option", "width", data.controlDef.width);
                                }
                                container.data("columns", columns);
                                container.data("comboMode", comboMode);
                                return container;
                            };
                            ComboBox.prototype.enable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass());
                                $wrapper.data("enable", true);
                                $wrapper.igCombo("option", "disabled", false);
                            };
                            ComboBox.prototype.disable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass());
                                $wrapper.data("enable", false);
                                $wrapper.igCombo("option", "disabled", true);
                            };
                            return ComboBox;
                        }(NtsControlBase));
                        var Button = (function (_super) {
                            __extends(Button, _super);
                            function Button() {
                                _super.apply(this, arguments);
                            }
                            Button.prototype.containerClass = function () {
                                return "nts-button-container";
                            };
                            Button.prototype.draw = function (data) {
                                var $container = $("<div/>").addClass(this.containerClass());
                                var $button = $("<button/>").addClass("ntsButton").css("height", "25px").appendTo($container)
                                    .text(data.controlDef.text || data.initValue).attr("tabindex", -1)
                                    .data("enable", data.enable).on("click", $.proxy(data.controlDef.click, null, data.rowObj));
                                $button.prop("disabled", !data.enable);
                                return $container;
                            };
                            Button.prototype.enable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                                $wrapper.find(".ntsButton").prop("disabled", false);
                            };
                            Button.prototype.disable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                                $wrapper.find(".ntsButton").prop("disabled", true);
                            };
                            return Button;
                        }(NtsControlBase));
                        var DeleteButton = (function (_super) {
                            __extends(DeleteButton, _super);
                            function DeleteButton() {
                                _super.apply(this, arguments);
                            }
                            DeleteButton.prototype.draw = function (data) {
                                var btnContainer = _super.prototype.draw.call(this, data);
                                var btn = btnContainer.find("button");
                                btn.off("click", data.controlDef.click);
                                btn.on("click", data.deleteRow);
                                return btn;
                            };
                            return DeleteButton;
                        }(Button));
                        var TextEditor = (function (_super) {
                            __extends(TextEditor, _super);
                            function TextEditor() {
                                _super.apply(this, arguments);
                            }
                            TextEditor.prototype.containerClass = function () {
                                return "nts-editor-container";
                            };
                            TextEditor.prototype.draw = function (data) {
                                var self = this;
                                var constraint = data.controlDef.constraint;
                                var $container = $("<div/>").addClass(this.containerClass());
                                var $input = $("<input/>").addClass("nts-editor nts-input").css({ padding: "2px", width: "96%" })
                                    .attr("tabindex", -1).val(data.initValue);
                                if (constraint.valueType === "Time")
                                    $input.css("text-align", "right");
                                var $editor = $("<span/>").addClass("nts-editor-wrapper ntsControl").css("width", "100%").append($input).appendTo($container);
                                var cell;
                                self.validate(data.controlDef, data.initValue).success(function (t) {
                                    $input.val(t);
                                    $input.data(internal.TXT_RAW, data.initValue);
                                }).terminate();
                                var valueToDs = function (valueType, before, after) {
                                    switch (valueType) {
                                        case "Integer":
                                        case "HalfInt":
                                        case "String":
                                            return before;
                                        case "Time":
                                            return after;
                                    }
                                };
                                $input.on(events.Handler.KEY_DOWN, function (evt) {
                                    if (utils.isEnterKey(evt)) {
                                        var value_2 = $input.val();
                                        self.validate(data.controlDef, value_2).success(function (t) {
                                            cell = self.cellBelongTo($input);
                                            errors.clear(self.$containedGrid, cell);
                                            var val = valueToDs(constraint.valueType, value_2, t);
                                            $input.data(internal.TXT_RAW, val);
                                            data.update(val);
                                        }).fail(function (errId) {
                                            cell = self.cellBelongTo($input);
                                            errors.set(self.$containedGrid, cell, uk.resource.getMessage(errId));
                                        }).terminate;
                                    }
                                });
                                $input.on(events.Handler.KEY_UP, function (evt) {
                                    self.validate(data.controlDef, $input.val()).success(function (t) {
                                        cell = self.cellBelongTo($input);
                                        errors.clear(self.$containedGrid, cell);
                                    }).fail(function (errId) {
                                        cell = self.cellBelongTo($input);
                                        errors.set(self.$containedGrid, cell, nts.uk.resource.getMessage(errId));
                                    }).terminate();
                                });
                                $input.on(events.Handler.BLUR, function (evt) {
                                    self.validate(data.controlDef, $input.val()).success(function (t) {
                                        var value = $input.val();
                                        cell = self.cellBelongTo($input);
                                        errors.clear(self.$containedGrid, cell);
                                        var val = valueToDs(constraint.valueType, value, t);
                                        data.update(val);
                                        $input.data(internal.TXT_RAW, val);
                                        $input.val(t);
                                    }).fail(function (errId) {
                                        cell = self.cellBelongTo($input);
                                        errors.set(self.$containedGrid, cell, nts.uk.resource.getMessage(errId));
                                    }).terminate();
                                });
                                $input.on(events.Handler.CLICK, function (evt) {
                                    var rawValue = $input.data(internal.TXT_RAW);
                                    if (!errors.any({ element: $input.closest("td")[0] })
                                        && !uk.util.isNullOrUndefined(rawValue))
                                        $input.val(rawValue);
                                });
                                return $container;
                            };
                            TextEditor.prototype.cellBelongTo = function ($input) {
                                var self = this;
                                var cell = {};
                                cell.element = $input.closest("td")[0];
                                var $gridControl = $input.closest("div[class*='nts-grid-control']");
                                if ($gridControl.length === 0)
                                    return;
                                var clazz = $gridControl.attr("class").split(" ")[0];
                                var pos = clazz.split("-");
                                cell.id = utils.parseIntIfNumber(pos.pop(), self.$containedGrid, utils.getColumnsMap(self.$containedGrid));
                                cell.columnKey = pos.pop();
                                return cell;
                            };
                            TextEditor.prototype.validate = function (controlDef, value) {
                                var constraint = controlDef.constraint;
                                if (constraint.required && (_.isEmpty(value) || _.isNull(value)))
                                    return validation.Result.invalid("FND_E_REQ_INPUT");
                                switch (constraint.valueType) {
                                    case "Integer":
                                        var valid = uk.ntsNumber.isNumber(value, false);
                                        if (!valid)
                                            return validation.Result.invalid("FND_E_INTEGER");
                                        var formatted = value;
                                        if (constraint.format === "Number_Separated") {
                                            formatted = uk.ntsNumber.formatNumber(value, { formatId: constraint.format });
                                        }
                                        return validation.Result.OK(formatted);
                                    case "Time":
                                        return validation.parseTime(value, constraint.format);
                                    case "HalfInt":
                                        if (uk.ntsNumber.isHalfInt(value)) {
                                            return validation.Result.OK(value);
                                        }
                                        return validation.Result.invalid("FND_E_HALFINT");
                                    case "String":
                                        return validation.Result.OK(value);
                                }
                            };
                            TextEditor.prototype.enable = function ($container) {
                                var self = this;
                                var $wrapper = $container.find("." + self.containerClass());
                                $wrapper.find("input").prop("disabled", false);
                            };
                            TextEditor.prototype.disable = function ($container) {
                                var self = this;
                                var $wrapper = $container.find("." + self.containerClass());
                                $wrapper.find("input").prop("disabled", true);
                            };
                            return TextEditor;
                        }(NtsControlBase));
                        var Label = (function (_super) {
                            __extends(Label, _super);
                            function Label(action) {
                                _super.call(this);
                                this.action = action;
                            }
                            Label.prototype.containerClass = function () {
                                return "nts-label-container";
                            };
                            Label.prototype.draw = function (data) {
                                var self = this;
                                var $container = $("<div/>").addClass(this.containerClass());
                                var $label = $("<label/>").addClass("ntsLabel").css({ padding: "3px 0px", display: "inline-block", width: "100%" }).text(data.text).appendTo($container);
                                if (self.action && _.isFunction(self.action)) {
                                    $container.on(events.Handler.CLICK, function (evt) {
                                        self.action();
                                    });
                                    $label.css({ cursor: "pointer" });
                                }
                                return $container;
                            };
                            Label.prototype.enable = function ($container) {
                                return;
                            };
                            Label.prototype.disable = function ($container) {
                                return;
                            };
                            return Label;
                        }(NtsControlBase));
                        var LinkLabel = (function (_super) {
                            __extends(LinkLabel, _super);
                            function LinkLabel() {
                                _super.apply(this, arguments);
                            }
                            LinkLabel.prototype.containerClass = function () {
                                return "nts-link-container";
                            };
                            LinkLabel.prototype.draw = function (data) {
                                return $('<div/>').addClass(this.containerClass()).append($("<a/>")
                                    .addClass("link-button").css({ backgroundColor: "inherit", color: "#0066CC" })
                                    .text(data.initValue).on("click", $.proxy(data.controlDef.click, null, data.rowId, data.columnKey)))
                                    .data("click", data.controlDef.click);
                            };
                            LinkLabel.prototype.enable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                                $wrapper.find("a").css("color", "deepskyblue").on("click", $wrapper.data("click"));
                            };
                            LinkLabel.prototype.disable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                                $wrapper.find("a").css("color", "#333").off("click");
                            };
                            return LinkLabel;
                        }(NtsControlBase));
                        var FlexImage = (function (_super) {
                            __extends(FlexImage, _super);
                            function FlexImage() {
                                _super.apply(this, arguments);
                            }
                            FlexImage.prototype.containerClass = function () {
                                return "nts-fleximage-container";
                            };
                            FlexImage.prototype.draw = function (data) {
                                var $container = $("<div/>").addClass(this.containerClass());
                                if (uk.util.isNullOrUndefined(data.initValue) || _.isEmpty(data.initValue))
                                    return $container;
                                var $image = $("<span/>").addClass(data.controlDef.source);
                                if (data.controlDef.click && _.isFunction(data.controlDef.click)) {
                                    $container.on(events.Handler.CLICK, $.proxy(data.controlDef.click, null, data.columnKey, data.rowId))
                                        .css({ cursor: "pointer" }).data(events.Handler.CLICK, data.controlDef.click);
                                }
                                return $container.append($image);
                            };
                            FlexImage.prototype.enable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", true);
                                $wrapper.on(events.Handler.CLICK, $wrapper.data(events.Handler.CLICK));
                            };
                            FlexImage.prototype.disable = function ($container) {
                                var $wrapper = $container.find("." + this.containerClass()).data("enable", false);
                                $wrapper.off(events.Handler.CLICK);
                            };
                            return FlexImage;
                        }(NtsControlBase));
                        var Image = (function (_super) {
                            __extends(Image, _super);
                            function Image() {
                                _super.apply(this, arguments);
                            }
                            Image.prototype.containerClass = function () {
                                return "nts-image-container";
                            };
                            Image.prototype.draw = function (data) {
                                return $("<div/>").addClass(this.containerClass()).append($("<span/>").addClass(data.controlDef.source));
                            };
                            Image.prototype.enable = function ($container) {
                            };
                            Image.prototype.disable = function ($container) {
                            };
                            return Image;
                        }(NtsControlBase));
                        var comboBox;
                        (function (comboBox) {
                            function getCopiedValue(cell, copiedText) {
                                var copiedValue;
                                var $comboBox = utils.comboBoxOfCell(cell);
                                if ($comboBox.length > 0) {
                                    var items = $comboBox.igCombo("items");
                                    var textKey_1 = $comboBox.igCombo("option", "textKey");
                                    var valueKey_1 = $comboBox.igCombo("option", "valueKey");
                                    _.forEach(items, function (item) {
                                        if (item.data[textKey_1] === copiedText.trim()) {
                                            copiedValue = item.data[valueKey_1];
                                            return false;
                                        }
                                    });
                                }
                                return copiedValue;
                            }
                            comboBox.getCopiedValue = getCopiedValue;
                        })(comboBox = ntsControls.comboBox || (ntsControls.comboBox = {}));
                    })(ntsControls || (ntsControls = {}));
                    var specialColumn;
                    (function (specialColumn_1) {
                        specialColumn_1.CODE = "code";
                        specialColumn_1.COMBO_CODE = "comboCode";
                        function ifTrue(columnSpecialTypes, column, bounceCombos, flatCols) {
                            if (uk.util.isNullOrUndefined(column.ntsType))
                                return;
                            if (column.ntsType === specialColumn_1.CODE) {
                                columnSpecialTypes[column.key] = { type: column.ntsType,
                                    onChange: column.onChange };
                            }
                            else if (column.ntsType === specialColumn_1.COMBO_CODE) {
                                columnSpecialTypes[column.key] = { type: column.ntsType,
                                    onChange: identity };
                                var index = _.findIndex(flatCols, function (o) {
                                    return o.key === column.key;
                                });
                                var b = void 0;
                                if (index + 1 < flatCols.length && (b = flatCols[index + 1]) !== undefined) {
                                    bounceCombos[b.key] = column.key;
                                }
                            }
                        }
                        specialColumn_1.ifTrue = ifTrue;
                        function tryDo($grid, cell, pastedText, visibleColumnsMap) {
                            var columnTypes = $grid.data(internal.SPECIAL_COL_TYPES);
                            var specialColumn;
                            var columnKey = cell.columnKey;
                            for (var key in columnTypes) {
                                if (key === columnKey) {
                                    specialColumn = columnTypes[key];
                                    break;
                                }
                            }
                            if (uk.util.isNullOrUndefined(specialColumn))
                                return;
                            visibleColumnsMap = !uk.util.isNullOrUndefined(visibleColumnsMap) ? visibleColumnsMap : utils.getVisibleColumnsMap($grid);
                            var isFixedColumn = utils.isFixedColumn(columnKey, visibleColumnsMap);
                            var nextColumn = utils.nextColumnByKey(visibleColumnsMap, columnKey, isFixedColumn);
                            if (uk.util.isNullOrUndefined(nextColumn) || nextColumn.index === 0)
                                return;
                            specialColumn.onChange(columnKey, cell.id, pastedText).done(function (res) {
                                var updatedRow = {};
                                var $gridRow = utils.rowAt(cell);
                                if (specialColumn.type === specialColumn_1.COMBO_CODE) {
                                    var $nextCell = $grid.igGrid("cellById", $gridRow.data("id"), nextColumn.options.key);
                                    var $comboContainer = $nextCell.find("." + ntsControls.COMBO_CLASS);
                                    var ds = $comboContainer.igCombo("option", "dataSource");
                                    var vKey_1 = $comboContainer.igCombo("option", "valueKey");
                                    if (uk.util.isNullOrUndefined(ds))
                                        return;
                                    var valueExists_1;
                                    _.forEach(ds._data, function (item) {
                                        if (item[vKey_1].toString() === String(res.toString().trim())) {
                                            valueExists_1 = true;
                                            return false;
                                        }
                                    });
                                    if (!valueExists_1)
                                        return;
                                }
                                if (nextColumn.options.dataType === "number") {
                                    updatedRow[nextColumn.options.key] = parseInt(res.toString().trim());
                                }
                                else {
                                    updatedRow[nextColumn.options.key] = String(res.toString().trim());
                                }
                                updating.updateRow($grid, $gridRow.data("id"), updatedRow, undefined, true);
                            }).fail(function (res) {
                            });
                            return true;
                        }
                        specialColumn_1.tryDo = tryDo;
                        function identity(key, id, value) {
                            var dfd = $.Deferred();
                            dfd.resolve(value);
                            return dfd.promise();
                        }
                    })(specialColumn || (specialColumn = {}));
                    var copyPaste;
                    (function (copyPaste) {
                        var CopyMode;
                        (function (CopyMode) {
                            CopyMode[CopyMode["SINGLE"] = 0] = "SINGLE";
                            CopyMode[CopyMode["MULTIPLE"] = 1] = "MULTIPLE";
                        })(CopyMode || (CopyMode = {}));
                        var PasteMode;
                        (function (PasteMode) {
                            PasteMode[PasteMode["NEW"] = 0] = "NEW";
                            PasteMode[PasteMode["UPDATE"] = 1] = "UPDATE";
                        })(PasteMode || (PasteMode = {}));
                        var Processor = (function () {
                            function Processor(options) {
                                this.pasteInMode = PasteMode.UPDATE;
                                this.options = options;
                            }
                            Processor.addFeatures = function (options) {
                                selection.addFeature(options);
                                return new Processor(options);
                            };
                            Processor.prototype.chainEvents = function ($grid, $target) {
                                var self = this;
                                self.$grid = $grid;
                                var target = !uk.util.isNullOrUndefined($target) ? $target : $grid;
                                events.Handler.pull(target).focusInWith(self).ctrlCxpWith(self);
                            };
                            Processor.prototype.copyHandler = function (cut) {
                                var selectedCells = selection.getSelectedCells(this.$grid);
                                var copiedData;
                                var checker = cut ? utils.isCuttableControls : utils.isCopiableControls;
                                nts.uk.ui.block.grayout();
                                if (selectedCells.length === 1) {
                                    this.copyMode = CopyMode.SINGLE;
                                    if (!checker(this.$grid, selectedCells[0].columnKey)) {
                                        nts.uk.ui.block.clear();
                                        return;
                                    }
                                    if (utils.isComboBox(this.$grid, selectedCells[0].columnKey)) {
                                        var $comboBox = utils.comboBoxOfCell(selectedCells[0]);
                                        if ($comboBox.length > 0) {
                                            copiedData = $comboBox.igCombo("text");
                                        }
                                    }
                                    else {
                                        var $cell = selectedCells[0].element;
                                        var origVal = $cell.data(internal.CELL_ORIG_VAL);
                                        copiedData = !uk.util.isNullOrUndefined(origVal) ? origVal : $cell.text();
                                    }
                                }
                                else {
                                    this.copyMode = CopyMode.MULTIPLE;
                                    copiedData = this.converseStructure(selectedCells, cut);
                                }
                                $("#copyHelper").val(copiedData).select();
                                document.execCommand("copy");
                                nts.uk.ui.block.clear();
                                return selectedCells;
                            };
                            Processor.prototype.converseStructure = function (cells, cut) {
                                var self = this;
                                var maxRow = 0;
                                var minRow = 0;
                                var maxColumn = 0;
                                var minColumn = 0;
                                var structure = [];
                                var structData = "";
                                var $tdCell, origVal;
                                var checker = cut ? utils.isCuttableControls : utils.isCopiableControls;
                                _.forEach(cells, function (cell, index) {
                                    var rowIndex = cell.rowIndex;
                                    var columnIndex = utils.getDisplayColumnIndex(self.$grid, cell);
                                    if (index === 0) {
                                        minRow = maxRow = rowIndex;
                                        minColumn = maxColumn = columnIndex;
                                    }
                                    if (rowIndex < minRow)
                                        minRow = rowIndex;
                                    if (rowIndex > maxRow)
                                        maxRow = rowIndex;
                                    if (columnIndex < minColumn)
                                        minColumn = columnIndex;
                                    if (columnIndex > maxColumn)
                                        maxColumn = columnIndex;
                                    if (uk.util.isNullOrUndefined(structure[rowIndex])) {
                                        structure[rowIndex] = {};
                                    }
                                    if (!checker(self.$grid, cell.columnKey))
                                        return;
                                    if (utils.isComboBox(self.$grid, cell.columnKey)) {
                                        var $comboBox = utils.comboBoxOfCell(cell);
                                        if ($comboBox.length > 0) {
                                            structure[rowIndex][columnIndex] = $comboBox.igCombo("text");
                                        }
                                    }
                                    else {
                                        $tdCell = cell.element;
                                        origVal = $tdCell.data(internal.CELL_ORIG_VAL);
                                        structure[rowIndex][columnIndex] = !uk.util.isNullOrUndefined(origVal) ? origVal : $tdCell.text();
                                    }
                                });
                                for (var i = minRow; i <= maxRow; i++) {
                                    for (var j = minColumn; j <= maxColumn; j++) {
                                        if (uk.util.isNullOrUndefined(structure[i]) || uk.util.isNullOrUndefined(structure[i][j])) {
                                            structData += "null";
                                        }
                                        else {
                                            structData += structure[i][j];
                                        }
                                        if (j === maxColumn)
                                            structData += "\n";
                                        else
                                            structData += "\t";
                                    }
                                }
                                return structData;
                            };
                            Processor.prototype.cutHandler = function () {
                                var self = this;
                                var selectedCells = this.copyHandler(true);
                                var cellsGroup = _.groupBy(selectedCells, "rowIndex");
                                _.forEach(Object.keys(cellsGroup), function (rowIdx) {
                                    var $row = utils.rowAt(cellsGroup[rowIdx][0]);
                                    var updatedRowData = {};
                                    _.forEach(cellsGroup[rowIdx], function (cell) {
                                        if (!utils.isCuttableControls(self.$grid, cell.columnKey))
                                            return;
                                        updatedRowData[cell.columnKey] = "";
                                    });
                                    updating.updateRow(self.$grid, $row.data("id"), updatedRowData);
                                });
                            };
                            Processor.prototype.pasteHandler = function (evt) {
                                nts.uk.ui.block.grayout();
                                if (this.copyMode === CopyMode.SINGLE) {
                                    this.pasteSingleCellHandler(evt);
                                }
                                else {
                                    this.pasteRangeHandler(evt);
                                }
                                nts.uk.ui.block.clear();
                            };
                            Processor.prototype.pasteSingleCellHandler = function (evt) {
                                var self = this;
                                var cbData = this.getClipboardContent(evt);
                                var selectedCells = selection.getSelectedCells(this.$grid);
                                var visibleColumnsMap = utils.getVisibleColumnsMap(self.$grid);
                                _.forEach(selectedCells, function (cell, index) {
                                    if (!utils.isPastableControls(self.$grid, cell.columnKey)
                                        || utils.isDisabled($(cell.element)))
                                        return;
                                    if (utils.isEditMode(self.$grid)) {
                                        var editor = self.$grid.igGridUpdating("editorForCell", cell.element);
                                        if (cell.element.has(editor).length > 0) {
                                            var inputs_1 = editor.find("input");
                                            inputs_1[0].value = cbData;
                                            inputs_1[1].value = cbData;
                                            setTimeout(function () {
                                                inputs_1[0].focus();
                                            }, 0);
                                            return;
                                        }
                                    }
                                    var rowIndex = cell.rowIndex;
                                    var columnIndex = cell.index;
                                    var $gridRow = utils.rowAt(cell);
                                    var updatedRow = {};
                                    var columnsGroup = utils.columnsGroupOfCell(cell, visibleColumnsMap);
                                    var columnKey = columnsGroup[columnIndex].key;
                                    if (utils.isComboBox(self.$grid, cell.columnKey)) {
                                        var copiedValue = ntsControls.comboBox.getCopiedValue(cell, cbData);
                                        if (!uk.util.isNullOrUndefined(copiedValue)) {
                                            updatedRow[columnKey] = columnsGroup[columnIndex].dataType === "number"
                                                ? parseInt(copiedValue) : copiedValue;
                                        }
                                        else {
                                            var $combo = cell.element.find(".nts-combo-container");
                                            var $comboInput = $($combo.find("input")[1]);
                                            $comboInput.ntsError("set", "Pasted text not valid");
                                            $combo.igCombo("text", "");
                                            return;
                                        }
                                    }
                                    else {
                                        setTimeout(function () {
                                            specialColumn.tryDo(self.$grid, cell, cbData, visibleColumnsMap);
                                        }, 1);
                                        if (columnsGroup[columnIndex].dataType === "number") {
                                            updatedRow[columnKey] = parseInt(cbData);
                                        }
                                        else {
                                            updatedRow[columnKey] = cbData;
                                        }
                                    }
                                    updating.updateRow(self.$grid, $gridRow.data("id"), updatedRow);
                                });
                            };
                            Processor.prototype.pasteRangeHandler = function (evt) {
                                var cbData = this.getClipboardContent(evt);
                                if (utils.isEditMode(this.$grid)) {
                                    cbData = this.processInEditMode(cbData);
                                    this.updateInEditMode(cbData);
                                }
                                else {
                                    cbData = this.process(cbData);
                                    this.pasteInMode === PasteMode.UPDATE ? this.updateWith(cbData) : this.addNew(cbData);
                                }
                            };
                            Processor.prototype.getClipboardContent = function (evt) {
                                if (window.clipboardData) {
                                    window.event.returnValue = false;
                                    return window.clipboardData.getData("text");
                                }
                                else {
                                    return evt.originalEvent.clipboardData.getData("text/plain");
                                }
                            };
                            Processor.prototype.processInEditMode = function (data) {
                                if (uk.util.isNullOrUndefined(data))
                                    return;
                                return data.split("\n")[0];
                            };
                            Processor.prototype.updateInEditMode = function (data) {
                                var selectedCell = selection.getSelectedCell(this.$grid);
                                var rowIndex = selectedCell.rowIndex;
                                var columnIndex = selectedCell.index;
                                var visibleColumnsMap = utils.getVisibleColumnsMap(this.$grid);
                                var updateRow = {};
                                var columnsGroup = utils.columnsGroupOfCell(selectedCell, visibleColumnsMap);
                                var columnKey = columnsGroup[columnIndex].key;
                                updateRow[columnKey] = data;
                                var $gridRow = utils.rowAt(selectedCell);
                                updating.updateRow(this.$grid, $gridRow.data("id"), updateRow);
                            };
                            Processor.prototype.process = function (data) {
                                var dataRows = _.map(data.split("\n"), function (row) {
                                    return row.split("\t");
                                });
                                var rowsCount = dataRows.length;
                                if ((dataRows[rowsCount - 1].length === 1 && dataRows[rowsCount - 1][0] === "")
                                    || dataRows.length === 1 && dataRows[0].length === 1
                                        && (dataRows[0][0] === "" || dataRows[0][0] === "\r")) {
                                    dataRows.pop();
                                }
                                return dataRows;
                            };
                            Processor.prototype.updateWith = function (data) {
                                var self = this;
                                if (!utils.selectable(this.$grid) || !utils.updatable(this.$grid))
                                    return;
                                var selectedCell = selection.getSelectedCell(this.$grid);
                                if (selectedCell === undefined)
                                    return;
                                selectedCell.element.focus();
                                var visibleColumnsMap = utils.getVisibleColumnsMap(self.$grid);
                                var visibleColumns = utils.visibleColumnsFromMap(visibleColumnsMap);
                                var columnIndex = selectedCell.index;
                                var rowIndex = selectedCell.rowIndex;
                                var targetCol = _.find(visibleColumns, function (column) {
                                    return column.key === selectedCell.columnKey;
                                });
                                if (uk.util.isNullOrUndefined(targetCol))
                                    return;
                                _.forEach(data, function (row, idx) {
                                    var $gridRow;
                                    if (idx === 0)
                                        $gridRow = utils.rowAt(selectedCell);
                                    else
                                        $gridRow = utils.nextNRow(selectedCell, idx);
                                    if (uk.util.isNullOrUndefined($gridRow))
                                        return;
                                    var rowData = {};
                                    var targetIndex = columnIndex;
                                    var targetCell = selectedCell;
                                    var targetColumn = targetCol;
                                    var comboErrors = [];
                                    var _loop_1 = function() {
                                        var nextColumn = void 0;
                                        var columnKey = targetColumn.key;
                                        var cellElement = self.$grid.igGrid("cellById", $gridRow.data("id"), columnKey);
                                        if ((!uk.util.isNullOrUndefined(row[i]) && row[i].trim() === "null")
                                            || !utils.isPastableControls(self.$grid, columnKey)
                                            || utils.isDisabled(cellElement)) {
                                            nextColumn = utils.nextColumn(visibleColumnsMap, targetIndex, targetColumn.fixed);
                                            targetColumn = nextColumn.options;
                                            targetIndex = nextColumn.index;
                                            return "continue";
                                        }
                                        var columnsGroup = utils.columnsGroupOfColumn(targetColumn, visibleColumnsMap);
                                        if (targetIndex > columnsGroup.length - 1)
                                            return "break";
                                        if (utils.isComboBox(self.$grid, columnKey)) {
                                            var cellContent = row[i].trim();
                                            var copiedValue = ntsControls.comboBox.getCopiedValue({ element: cellElement[0] }, cellContent);
                                            if (!uk.util.isNullOrUndefined(copiedValue)) {
                                                rowData[columnKey] = targetColumn.dataType === "number" ? parseInt(copiedValue) : copiedValue;
                                            }
                                            else {
                                                comboErrors.push({ cell: cellElement, content: cellContent });
                                                nextColumn = utils.nextColumn(visibleColumnsMap, targetIndex, targetColumn.fixed);
                                                targetColumn = nextColumn.options;
                                                targetIndex = nextColumn.index;
                                                return "continue";
                                            }
                                        }
                                        else {
                                            var cell_1 = {};
                                            cell_1.columnKey = columnKey;
                                            cell_1.element = cellElement;
                                            cell_1.id = $gridRow.data("id");
                                            cell_1.index = targetIndex;
                                            cell_1.row = $gridRow;
                                            cell_1.rowIndex = $gridRow.data("rowIdx");
                                            (function (i) {
                                                setTimeout(function () {
                                                    specialColumn.tryDo(self.$grid, cell_1, row[i].trim(), visibleColumnsMap);
                                                }, 1);
                                            })(i);
                                            if (targetColumn.dataType === "number") {
                                                rowData[columnKey] = parseInt(row[i]);
                                            }
                                            else {
                                                rowData[columnKey] = row[i];
                                            }
                                        }
                                        nextColumn = utils.nextColumn(visibleColumnsMap, targetIndex, targetColumn.fixed);
                                        targetColumn = nextColumn.options;
                                        targetIndex = nextColumn.index;
                                    };
                                    for (var i = 0; i < row.length; i++) {
                                        var state_1 = _loop_1();
                                        if (state_1 === "break") break;
                                        if (state_1 === "continue") continue;
                                    }
                                    updating.updateRow(self.$grid, $gridRow.data("id"), rowData);
                                    _.forEach(comboErrors, function (combo) {
                                        setTimeout(function () {
                                            var $container = combo.cell.find(".nts-combo-container");
                                            var $comboInput = $($container.find("input")[1]);
                                            $comboInput.ntsError("set", "Pasted text not valid");
                                            $container.igCombo("text", combo.content);
                                        }, 0);
                                    });
                                });
                            };
                            Processor.prototype.addNew = function (data) {
                                var self = this;
                                var visibleColumns = null;
                                if (!this.pasteable(data[0].length - visibleColumns.length))
                                    return;
                                _.forEach(data, function (row, idx) {
                                    var rowData = {};
                                    for (var i = 0; i < visibleColumns.length; i++) {
                                        var columnKey = visibleColumns[i].key;
                                        if (visibleColumns[i].dataType === "number") {
                                            rowData[columnKey] = parseInt(row[i]);
                                        }
                                        else {
                                            rowData[columnKey] = row[i];
                                        }
                                    }
                                    self.$grid.igGridUpdating("addRow", rowData);
                                });
                            };
                            Processor.prototype.pasteable = function (excessColumns) {
                                if (excessColumns > 0) {
                                    nts.uk.ui.dialog.alert("Copied table structure doesn't match.");
                                    return false;
                                }
                                return true;
                            };
                            return Processor;
                        }());
                        copyPaste.Processor = Processor;
                        function ifOn($grid, options) {
                            if (options.ntsFeatures === undefined)
                                return;
                            _.forEach(options.ntsFeatures, function (f) {
                                if (f.name === feature.COPY_PASTE) {
                                    Processor.addFeatures(options).chainEvents($grid);
                                    return false;
                                }
                            });
                        }
                        copyPaste.ifOn = ifOn;
                    })(copyPaste || (copyPaste = {}));
                    var events;
                    (function (events) {
                        var Handler = (function () {
                            function Handler($grid, options) {
                                this.$grid = $grid;
                                this.options = options;
                                this.preventEditInError = !uk.util.isNullOrUndefined(options) ? options.preventEditInError : undefined;
                            }
                            Handler.pull = function ($grid, options) {
                                return new Handler($grid, options);
                            };
                            Handler.prototype.turnOn = function ($mainGrid) {
                                if (feature.isEnable(this.options.ntsFeatures, feature.CELL_EDIT)) {
                                    this.filter($mainGrid).onCellUpdate().onCellUpdateKeyUp();
                                }
                                if (!uk.util.isNullOrUndefined(this.options.enter)
                                    && (utils.selectable(this.$grid) || utils.selectable($mainGrid))) {
                                    this.onDirectEnter();
                                }
                                if (utils.selectable(this.$grid) || utils.selectable($mainGrid)) {
                                    this.onSpacePress();
                                }
                                if (feature.isEnable(this.options.features, feature.RESIZING)) {
                                    this.onColumnResizing();
                                }
                            };
                            Handler.prototype.onDirectEnter = function () {
                                var direction = new selection.Direction();
                                if (!direction.to)
                                    direction.to = this.options.enter;
                                this.$grid.on(Handler.KEY_DOWN, $.proxy(direction.bind, direction));
                                this.$grid.data(internal.ENTER_DIRECT, direction);
                                return this;
                            };
                            Handler.prototype.onCellUpdate = function () {
                                var self = this;
                                this.$grid.on(Handler.KEY_DOWN, function (evt) {
                                    if (evt.ctrlKey)
                                        return;
                                    var selectedCell = selection.getSelectedCell(self.$grid);
                                    updating.triggerCellUpdate(evt, selectedCell);
                                });
                                return this;
                            };
                            Handler.prototype.onCellUpdateKeyUp = function () {
                                var self = this;
                                this.$grid.on(Handler.KEY_UP, function (evt) {
                                    if (evt.ctrlKey)
                                        return;
                                    var selectedCell = selection.getSelectedCell(self.$grid);
                                    updating.onEditCell(evt, selectedCell);
                                });
                                return this;
                            };
                            Handler.prototype.onSpacePress = function () {
                                var self = this;
                                self.$grid.on(Handler.KEY_DOWN, function (evt) {
                                    if (!utils.isSpaceKey(evt))
                                        return;
                                    var selectedCell = selection.getSelectedCell(self.$grid);
                                    if (uk.util.isNullOrUndefined(selectedCell))
                                        return;
                                    var checkbox = $(selectedCell.element).find(".nts-checkbox-container");
                                    if (checkbox.length > 0) {
                                        checkbox.find("input[type='checkbox']").click();
                                    }
                                });
                                return this;
                            };
                            Handler.prototype.focusInWith = function (processor) {
                                this.$grid.on(Handler.FOCUS_IN, function (evt) {
                                    if ($("#pasteHelper").length > 0 && $("#copyHelper").length > 0)
                                        return;
                                    var pasteArea = $("<textarea id='pasteHelper'/>").css({ "opacity": 0, "overflow": "hidden" })
                                        .on("paste", $.proxy(processor.pasteHandler, processor));
                                    var copyArea = $("<textarea id='copyHelper'/>").css({ "opacity": 0, "overflow": "hidden" });
                                    $("<div/>").css({ "position": "fixed", "top": -10000, "left": -10000 })
                                        .appendTo($(document.body)).append(pasteArea).append(copyArea);
                                });
                                return this;
                            };
                            Handler.prototype.ctrlCxpWith = function (processor) {
                                this.$grid.on(Handler.KEY_DOWN, function (evt) {
                                    if (evt.ctrlKey && utils.isPasteKey(evt)) {
                                        $("#pasteHelper").focus();
                                    }
                                    else if (evt.ctrlKey && utils.isCopyKey(evt)) {
                                        processor.copyHandler();
                                    }
                                    else if (evt.ctrlKey && utils.isCutKey(evt)) {
                                    }
                                });
                                return this;
                            };
                            Handler.prototype.filter = function ($target) {
                                var self = this;
                                var $mainGrid = !uk.util.isNullOrUndefined($target) ? $target : self.$grid;
                                self.$grid.on(Handler.KEY_DOWN, function (evt) {
                                    if (utils.isAlphaNumeric(evt) || utils.isMinusSymbol(evt) || utils.isDeleteKey(evt)) {
                                        var cell = selection.getSelectedCell($mainGrid);
                                        if (cell === undefined || updating.containsNtsControl($(evt.target)))
                                            evt.stopImmediatePropagation();
                                        return;
                                    }
                                    if (utils.isTabKey(evt) && utils.isErrorStatus($mainGrid)) {
                                        evt.preventDefault();
                                        evt.stopImmediatePropagation();
                                    }
                                });
                                if (this.preventEditInError) {
                                    self.$grid[0].addEventListener(Handler.MOUSE_DOWN, function (evt) {
                                        if (utils.isNotErrorCell($mainGrid, evt)) {
                                            evt.preventDefault();
                                            evt.stopImmediatePropagation();
                                        }
                                    }, true);
                                    self.$grid[0].addEventListener(Handler.CLICK, function (evt) {
                                        if (utils.isNotErrorCell($mainGrid, evt)) {
                                            evt.preventDefault();
                                            evt.stopImmediatePropagation();
                                        }
                                    }, true);
                                }
                                return this;
                            };
                            Handler.prototype.onColumnResizing = function () {
                                var self = this;
                                self.$grid.on(Handler.COLUMN_RESIZING, function (evt, args) {
                                    columnSize.save(self.$grid, args.columnKey, args.desiredWidth);
                                });
                                return this;
                            };
                            Handler.KEY_DOWN = "keydown";
                            Handler.KEY_UP = "keyup";
                            Handler.FOCUS_IN = "focusin";
                            Handler.BLUR = "blur";
                            Handler.CLICK = "click";
                            Handler.MOUSE_DOWN = "mousedown";
                            Handler.SCROLL = "scroll";
                            Handler.GRID_EDIT_CELL_STARTED = "iggridupdatingeditcellstarted";
                            Handler.COLUMN_RESIZING = "iggridresizingcolumnresizing";
                            Handler.RECORDS = "iggridvirtualrecordsrender";
                            Handler.CELL_CLICK = "iggridcellclick";
                            Handler.PAGE_INDEX_CHANGE = "iggridpagingpageindexchanging";
                            Handler.PAGE_SIZE_CHANGE = "iggridpagingpagesizechanging";
                            Handler.CONTROL_CHANGE = "ntsgridcontrolvaluechanged";
                            return Handler;
                        }());
                        events.Handler = Handler;
                        function afterRendered(options, cbSelectionColumns) {
                            options.rendered = function (evt, ui) {
                                var $grid = $(evt.target);
                                events.Handler.pull($grid, options).turnOn();
                                var cbSelect = $grid.data(internal.CB_SELECTED);
                                if (cbSelect) {
                                    _.merge(cbSelect, cbSelectionColumns);
                                }
                                else {
                                    $grid.data(internal.CB_SELECTED, cbSelectionColumns);
                                }
                                var $fixedTbl = fixedColumns.getFixedTable($grid);
                                if ($fixedTbl.length > 0) {
                                    if (feature.isEnable(options.ntsFeatures, feature.COPY_PASTE))
                                        new copyPaste.Processor().chainEvents($grid, $fixedTbl);
                                    events.Handler.pull($fixedTbl, options).turnOn($grid);
                                }
                                var sheetConfig = $grid.data(internal.SHEETS);
                                sheet.onScroll($grid);
                                if (!uk.util.isNullOrUndefined(sheetConfig) && !uk.util.isNullOrUndefined(sheetConfig.currentPosition)) {
                                    $grid.igGrid("virtualScrollTo", sheetConfig.currentPosition);
                                }
                                var selectedCell = $grid.data(internal.SELECTED_CELL);
                                if (!uk.util.isNullOrUndefined(selectedCell)) {
                                    var fixedColumns_2 = utils.getVisibleFixedColumns($grid);
                                    if (_.find(fixedColumns_2, function (col) {
                                        return col.key === selectedCell.columnKey;
                                    }) !== undefined) {
                                        setTimeout(function () {
                                            selection.selectCell($grid, selectedCell.rowIndex, selectedCell.index, true);
                                        }, 1);
                                    }
                                }
                                errors.mark($grid);
                                color.styleHeaders($grid, options);
                                if (options.autoFitWindow) {
                                    settings.setGridSize($grid);
                                }
                                columnSize.load($grid);
                                utils.setChildrenTabIndex($grid, -1);
                            };
                        }
                        events.afterRendered = afterRendered;
                        function onCellClick($grid) {
                            $grid.on(Handler.CELL_CLICK, function (evt, ui) {
                                if (!utils.isEditMode($grid) && errors.any({ element: ui.cellElement })) {
                                    _.defer(function () {
                                        var $editor = $(ui.cellElement).find(errors.EDITOR_SELECTOR);
                                        if ($editor.length === 0)
                                            return;
                                        $editor.css(errors.ERROR_STL);
                                    });
                                }
                            });
                        }
                        events.onCellClick = onCellClick;
                    })(events || (events = {}));
                    var validation;
                    (function (validation) {
                        validation.VALIDATORS = "ntsValidators";
                        var H_M_MAX = 60;
                        var ColumnFieldValidator = (function () {
                            function ColumnFieldValidator(name, primitiveValue, options) {
                                this.name = name;
                                this.primitiveValue = primitiveValue;
                                this.options = options;
                            }
                            ColumnFieldValidator.prototype.probe = function (value) {
                                var valueType = this.primitiveValue ? ui.validation.getConstraint(this.primitiveValue).valueType
                                    : this.options.cDisplayType;
                                switch (valueType) {
                                    case "String":
                                        return new nts.uk.ui.validation.StringValidator(this.name, this.primitiveValue, this.options)
                                            .validate(value, this.options);
                                    case "Integer":
                                    case "Decimal":
                                    case "HalfInt":
                                        return new NumberValidator(this.name, valueType, this.primitiveValue, this.options)
                                            .validate(value);
                                    case "Currency":
                                        var opts = new ui.option.CurrencyEditorOption();
                                        opts.grouplength = this.options.groupLength | 3;
                                        opts.decimallength = this.options.decimalLength | 2;
                                        opts.currencyformat = this.options.currencyFormat ? this.options.currencyFormat : "JPY";
                                        return new NumberValidator(this.name, valueType, this.primitiveValue, opts)
                                            .validate(value);
                                    case "Time":
                                        this.options.mode = "time";
                                        return new nts.uk.ui.validation.TimeValidator(this.name, this.primitiveValue, this.options)
                                            .validate(value);
                                    case "Clock":
                                        this.options.mode = "time";
                                        return new nts.uk.ui.validation.TimeValidator(this.name, this.primitiveValue, this.options)
                                            .validate(value);
                                    case "TimeWithDay":
                                        this.options.timeWithDay = true;
                                        var result = new ui.validation.TimeWithDayValidator(this.name, this.primitiveValue, this.options)
                                            .validate(value);
                                        if (result.isValid) {
                                            var formatter = new uk.text.TimeWithDayFormatter(this.options);
                                            result.parsedValue = formatter.format(result.parsedValue);
                                        }
                                        return result;
                                }
                            };
                            return ColumnFieldValidator;
                        }());
                        validation.ColumnFieldValidator = ColumnFieldValidator;
                        var NumberValidator = (function () {
                            function NumberValidator(name, displayType, primitiveValue, options) {
                                this.name = name;
                                this.displayType = displayType;
                                this.primitiveValue = primitiveValue;
                                this.options = options;
                            }
                            NumberValidator.prototype.validate = function (text) {
                                var self = this;
                                if (self.primitiveValue) {
                                    return new nts.uk.ui.validation.NumberValidator(self.name, self.primitiveValue, self.options).validate(text);
                                }
                                if (self.displayType === "Currency") {
                                    text = uk.text.replaceAll(text, self.options.groupseperator, "");
                                }
                                var result = new ui.validation.ValidationResult();
                                if ((uk.util.isNullOrUndefined(text) || text.length === 0)) {
                                    if (self.options && self.options.required) {
                                        result.fail(nts.uk.resource.getMessage('FND_E_REQ_INPUT', [self.name]), 'FND_E_REQ_INPUT');
                                        return result;
                                    }
                                    if (!self.options || (self.options && !self.options.required)) {
                                        result.success(text);
                                        return result;
                                    }
                                }
                                var message = {};
                                var isValid;
                                if (self.displayType === "HalfInt") {
                                    isValid = uk.ntsNumber.isHalfInt(text, message);
                                }
                                else if (self.displayType === "Integer") {
                                    isValid = uk.ntsNumber.isNumber(text, false, self.options, message);
                                }
                                else if (self.displayType === "Decimal" || self.displayType === "Currency") {
                                    isValid = uk.ntsNumber.isNumber(text, true, self.options, message);
                                }
                                var min = 0, max = 999999999;
                                var value = parseFloat(text);
                                if (!uk.util.isNullOrUndefined(self.options.min)) {
                                    min = self.options.min;
                                    if (value < min)
                                        isValid = false;
                                }
                                if (!uk.util.isNullOrUndefined(self.options.max)) {
                                    max = self.options.max;
                                    if (value > max)
                                        isValid = false;
                                }
                                if (!isValid) {
                                    result.fail(uk.resource.getMessage(message.id, [self.name, min, max]), message.id);
                                    return result;
                                }
                                var formatter = new uk.text.NumberFormatter({ option: self.options });
                                var formatted = formatter.format(text);
                                result.success(self.displayType === "Currency" ? formatted : value + "");
                                return result;
                            };
                            return NumberValidator;
                        }());
                        var Result = (function () {
                            function Result(isValid, formatted, messageId) {
                                this.onSuccess = $.noop;
                                this.onFail = $.noop;
                                this.isValid = isValid;
                                this.formatted = formatted;
                                this.errorMessageId = messageId;
                            }
                            Result.OK = function (formatted) {
                                return new Result(true, formatted);
                            };
                            Result.invalid = function (msgId) {
                                return new Result(false, null, msgId);
                            };
                            Result.prototype.success = function (cnt) {
                                this.onSuccess = cnt;
                                return this;
                            };
                            Result.prototype.fail = function (cnt) {
                                this.onFail = cnt;
                                return this;
                            };
                            Result.prototype.terminate = function () {
                                var self = this;
                                if (self.isValid && self.onSuccess && _.isFunction(self.onSuccess)) {
                                    self.onSuccess(self.formatted);
                                }
                                else if (!self.isValid && self.onFail && _.isFunction(self.onFail)) {
                                    self.onFail(self.errorMessageId);
                                }
                            };
                            return Result;
                        }());
                        validation.Result = Result;
                        function getValidators(columnsDef) {
                            var validators = {};
                            _.forEach(columnsDef, function (def) {
                                if (def.constraint === undefined)
                                    return;
                                validators[def.key] = new ColumnFieldValidator(def.headerText, def.constraint.primitiveValue, def.constraint);
                            });
                            return validators;
                        }
                        function scanValidators($grid, columnsDef) {
                            var columns = utils.analyzeColumns(columnsDef);
                            $grid.data(validation.VALIDATORS, getValidators(columns));
                            return columns;
                        }
                        validation.scanValidators = scanValidators;
                        function parseTime(value, format) {
                            if (uk.ntsNumber.isNumber(value, false)) {
                                if (value <= H_M_MAX)
                                    return Result.OK(value);
                                var hh = Math.floor(value / 100);
                                var mm = value % 100;
                                if (mm >= H_M_MAX)
                                    return Result.invalid("NEED_MSG_INVALID_TIME_FORMAT");
                                return Result.OK(hh + ":" + mm.toLocaleString("en-US", { minimumIntegerDigits: 2, useGrouping: false }));
                            }
                            var formatRes = uk.time.applyFormat(format, value, undefined);
                            if (!formatRes)
                                return Result.invalid("NEED_MSG_INVALID_TIME_FORMAT");
                            return Result.OK(formatRes);
                        }
                        validation.parseTime = parseTime;
                        function getValueType($grid, columnKey) {
                            var validators = $grid.data(validation.VALIDATORS);
                            if (!validators || !validators[columnKey])
                                return;
                            var column = validators[columnKey];
                            return column.primitiveValue ? ui.validation.getConstraint(column.primitiveValue).valueType
                                : column.options.cDisplayType;
                        }
                        validation.getValueType = getValueType;
                        function getGroupSeparator($grid, columnKey) {
                            var validators = $grid.data(validation.VALIDATORS);
                            if (!validators || !validators[columnKey])
                                return;
                            return validators[columnKey].options.groupseperator;
                        }
                        validation.getGroupSeparator = getGroupSeparator;
                    })(validation || (validation = {}));
                    var errors;
                    (function (errors) {
                        errors.HAS_ERROR = "hasError";
                        errors.ERROR_STL = { "border-color": "#ff6666" };
                        errors.NO_ERROR_STL = { "border-color": "" };
                        errors.EDITOR_SELECTOR = "div.ui-igedit-container";
                        var GridCellError = (function () {
                            function GridCellError(grid, rowId, columnKey, message) {
                                this.grid = grid;
                                this.rowId = rowId;
                                this.columnKey = columnKey;
                                this.message = message;
                                this.setColumnName();
                            }
                            GridCellError.prototype.setColumnName = function () {
                                var _this = this;
                                var allCols = utils.getColumns(this.grid);
                                if (!allCols)
                                    return;
                                var col = allCols.filter(function (c) { return c.key === _this.columnKey; });
                                if (col.length > 0) {
                                    this.columnName = col[0].headerText;
                                }
                            };
                            GridCellError.prototype.equals = function (err) {
                                if (!this.grid.is(err.grid))
                                    return false;
                                if (this.rowId !== err.rowId)
                                    return false;
                                if (this.columnKey !== err.columnKey)
                                    return false;
                                return true;
                            };
                            return GridCellError;
                        }());
                        errors.GridCellError = GridCellError;
                        function addCellError($grid, error) {
                            var gridErrors = $grid.data(internal.ERRORS);
                            if (!gridErrors) {
                                $grid.data(internal.ERRORS, [error]);
                                return;
                            }
                            if (gridErrors.some(function (e) {
                                return e.equals(error);
                            }))
                                return;
                            gridErrors.push(error);
                        }
                        function removeCellError($grid, rowId, key) {
                            var gridErrors = $grid.data(internal.ERRORS);
                            if (!gridErrors)
                                return;
                            _.remove(gridErrors, function (e) {
                                return $grid.is(e.grid) && rowId === e.rowId && key === e.columnKey;
                            });
                        }
                        function mark($grid) {
                            var errorsLog = $grid.data(internal.ERRORS_LOG);
                            if (uk.util.isNullOrUndefined(errorsLog))
                                return;
                            var sheets = $grid.data(internal.SHEETS);
                            var sheetErrors = errorsLog[sheets.currentSheet];
                            if (uk.util.isNullOrUndefined(sheetErrors))
                                return;
                            _.forEach(sheetErrors, function (cell) {
                                var $cell = $grid.igGrid("cellById", cell.id, cell.columnKey);
                                decorate($cell);
                            });
                        }
                        errors.mark = mark;
                        function decorate($cell) {
                            $cell.addClass(errors.HAS_ERROR);
                            $cell.css(errors.ERROR_STL);
                            var $editor = $cell.find(errors.EDITOR_SELECTOR);
                            if ($editor.length > 0)
                                $editor.css(errors.ERROR_STL);
                        }
                        function set($grid, cell, message) {
                            if (!cell || !cell.element || any(cell))
                                return;
                            var $cell = $(cell.element);
                            decorate($cell);
                            var errorDetails = createErrorInfos($grid, cell, message);
                            var setting = $grid.data(internal.SETTINGS);
                            if (setting.errorsOnPage) {
                                ui.errors.addCell(errorDetails);
                            }
                            addCellError($grid, errorDetails);
                            addErrorInSheet($grid, cell);
                        }
                        errors.set = set;
                        function createErrorInfos($grid, cell, message) {
                            var record = $grid.igGrid("findRecordByKey", cell.id);
                            var setting = $grid.data(internal.SETTINGS);
                            var error = new GridCellError($grid, cell.id, cell.columnKey, message);
                            var headers;
                            if (setting.errorsOnPage) {
                                var columns = ko.toJS(ui.errors.errorsViewModel().option().headers());
                                if (columns) {
                                    headers = columns.filter(function (c) { return c.visible; }).map(function (c) { return c.name; });
                                }
                            }
                            else {
                                headers = setting.errorColumns;
                            }
                            _.forEach(headers, function (header) {
                                if (uk.util.isNullOrUndefined(record[header])
                                    || !uk.util.isNullOrUndefined(error[header]))
                                    return;
                                error[header] = record[header];
                            });
                            return error;
                        }
                        function clear($grid, cell) {
                            if (!cell || !cell.element || !any(cell))
                                return;
                            var $cell = $(cell.element);
                            $cell.removeClass(errors.HAS_ERROR);
                            $cell.css(errors.NO_ERROR_STL);
                            var $editor = $cell.find(errors.EDITOR_SELECTOR);
                            if ($editor.length > 0)
                                $editor.css(errors.NO_ERROR_STL);
                            var setting = $grid.data(internal.SETTINGS);
                            if (setting.errorsOnPage) {
                                ui.errors.removeCell($grid, cell.id, cell.columnKey);
                            }
                            removeCellError($grid, cell.id, cell.columnKey);
                            removeErrorFromSheet($grid, cell);
                        }
                        errors.clear = clear;
                        function any(cell) {
                            return cell.element && $(cell.element).hasClass(errors.HAS_ERROR);
                        }
                        errors.any = any;
                        function addErrorInSheet($grid, cell) {
                            var errorsLog = $grid.data(internal.ERRORS_LOG) || {};
                            var sheets = $grid.data(internal.SHEETS);
                            if (uk.util.isNullOrUndefined(errorsLog[sheets.currentSheet])) {
                                errorsLog[sheets.currentSheet] = [];
                            }
                            errorsLog[sheets.currentSheet].push(cell);
                            $grid.data(internal.ERRORS_LOG, errorsLog);
                        }
                        function removeErrorFromSheet($grid, cell) {
                            var errorsLog = $grid.data(internal.ERRORS_LOG);
                            if (uk.util.isNullOrUndefined(errorsLog))
                                return;
                            var sheets = $grid.data(internal.SHEETS);
                            var sheetErrors = errorsLog[sheets.currentSheet];
                            if (uk.util.isNullOrUndefined(sheetErrors)) {
                                removeErrorBasedOtherSheet(sheets, errorsLog, cell);
                                return;
                            }
                            var cellErrorIdx;
                            _.forEach(sheetErrors, function (errorCell, i) {
                                if (cellEquals(errorCell, cell)) {
                                    cellErrorIdx = i;
                                    return false;
                                }
                            });
                            if (!uk.util.isNullOrUndefined(cellErrorIdx)) {
                                errorsLog[sheets.currentSheet].splice(cellErrorIdx, 1);
                            }
                            else {
                                removeErrorBasedOtherSheet(sheets, errorsLog, cell);
                            }
                        }
                        function removeErrorBasedOtherSheet(sheets, errorsLog, cell) {
                            var sheetsIn = sheets.columnsInSheetImme[cell.columnKey];
                            if (sheetsIn && sheetsIn.size > 1) {
                                _.forEach(Array.from(sheetsIn), function (s, i) {
                                    if (s !== sheets.currentSheet) {
                                        var oErrs = errorsLog[s];
                                        if (oErrs) {
                                            _.remove(oErrs, function (e) { return cellEquals(e, cell); });
                                        }
                                    }
                                });
                            }
                        }
                        function markIfError($grid, cell) {
                            var errorsLog = $grid.data(internal.ERRORS_LOG);
                            if (uk.util.isNullOrUndefined(errorsLog))
                                return;
                            var sheets = $grid.data(internal.SHEETS);
                            var sheetErrors = errorsLog[sheets.currentSheet];
                            if (uk.util.isNullOrUndefined(sheetErrors)) {
                                markBasedOtherSheet(sheets, errorsLog, cell);
                                return;
                            }
                            var marked = false;
                            _.forEach(sheetErrors, function (c) {
                                if (cellEquals(c, cell)) {
                                    decorate($(cell.element));
                                    marked = true;
                                    return false;
                                }
                            });
                            if (!marked) {
                                markBasedOtherSheet(sheets, errorsLog, cell);
                            }
                        }
                        errors.markIfError = markIfError;
                        function markBasedOtherSheet(sheets, errorsLog, cell) {
                            var sheetsIn = sheets.columnsInSheetImme[cell.columnKey];
                            if (sheetsIn && sheetsIn.size > 1) {
                                _.forEach(Array.from(sheetsIn), function (s, i) {
                                    if (s !== sheets.currentSheet) {
                                        var marked_1 = false;
                                        _.forEach(errorsLog[s], function (c) {
                                            if (cellEquals(c, cell)) {
                                                decorate($(cell.element));
                                                marked_1 = true;
                                                return false;
                                            }
                                        });
                                        if (marked_1)
                                            return false;
                                    }
                                });
                            }
                        }
                        function cellEquals(one, other) {
                            if (one.columnKey !== other.columnKey)
                                return false;
                            if (one.id !== other.id)
                                return false;
                            return true;
                        }
                    })(errors || (errors = {}));
                    var color;
                    (function (color) {
                        color.Error = "ntsgrid-error";
                        color.Alarm = "ntsgrid-alarm";
                        color.ManualEditTarget = "ntsgrid-manual-edit-target";
                        color.ManualEditOther = "ntsgrid-manual-edit-other";
                        color.Reflect = "ntsgrid-reflect";
                        color.Calculation = "ntsgrid-calc";
                        color.Disable = "ntsgrid-disable";
                        var CellFormatter = (function () {
                            function CellFormatter($grid, features, ntsFeatures, flatCols) {
                                this.$grid = $grid;
                                this.cellStateFeatureDef = feature.find(ntsFeatures, feature.CELL_STATE);
                                this.setStatesTable(ntsFeatures);
                                this.rowDisableFeatureDef = feature.find(ntsFeatures, feature.ROW_STATE);
                                if (!uk.util.isNullOrUndefined(this.rowDisableFeatureDef)
                                    && !uk.util.isNullOrUndefined(this.rowDisableFeatureDef.rows)) {
                                    this.disableRows = _.groupBy(this.rowDisableFeatureDef.rows, "rowId");
                                    this.addDisableRows(features, ntsFeatures, flatCols);
                                }
                                this.textColorFeatureDef = feature.find(ntsFeatures, feature.TEXT_COLOR);
                                this.setTextColorsTableMap(ntsFeatures);
                                this.textStyleFeatureDef = feature.find(ntsFeatures, feature.TEXT_STYLE);
                                this.setTextStylesTableMap();
                            }
                            CellFormatter.prototype.addDisableRows = function (features, ntsFeatures, flatCols) {
                                var self = this;
                                var sheetMng = self.$grid.data(internal.SHEETS);
                                var columns;
                                if (sheetMng) {
                                    columns = sheetMng.sheetColumns[sheetMng.currentSheet];
                                }
                                else {
                                    var sheetFt_1 = feature.find(ntsFeatures, feature.SHEET);
                                    if (sheetFt_1) {
                                        var sheetDf_1 = sheetFt_1.sheets.filter(function (s) {
                                            return s.name === sheetFt_1.initialDisplay;
                                        })[0];
                                        if (!sheetDf_1)
                                            return;
                                        self.rowDisableFeatureDef.rows.forEach(function (i) {
                                            sheetDf_1.columns.forEach(function (c) {
                                                self.addDisableState(i.rowId, c);
                                            });
                                        });
                                        var columnFixingFt_1 = feature.find(features, feature.COLUMN_FIX);
                                        if (columnFixingFt_1) {
                                            self.rowDisableFeatureDef.rows.forEach(function (i) {
                                                columnFixingFt_1.columnSettings.forEach(function (c) {
                                                    self.addDisableState(i.rowId, c.columnKey);
                                                });
                                            });
                                        }
                                        return;
                                    }
                                    else {
                                        columns = flatCols;
                                    }
                                }
                                if (columns) {
                                    var setCellDisable_1 = function (cols) {
                                        cols.forEach(function (c) {
                                            if (c.group) {
                                                setCellDisable_1(c.group);
                                                return;
                                            }
                                            self.rowDisableFeatureDef.rows.forEach(function (i) {
                                                self.addDisableState(i.rowId, c.key);
                                            });
                                        });
                                    };
                                    setCellDisable_1(columns);
                                }
                            };
                            CellFormatter.prototype.setStatesTable = function (features) {
                                var _this = this;
                                var self = this;
                                if (uk.util.isNullOrUndefined(this.cellStateFeatureDef))
                                    return;
                                var rowIdName = this.cellStateFeatureDef.rowId;
                                var columnKeyName = this.cellStateFeatureDef.columnKey;
                                var stateName = this.cellStateFeatureDef.state;
                                this.statesTable = this.cellStateFeatureDef.states;
                                this.rowStates = _.groupBy(this.statesTable, rowIdName);
                                _.forEach(this.rowStates, function (value, key) {
                                    _this.rowStates[key] = _.groupBy(_this.rowStates[key], function (item) {
                                        if (item[stateName].indexOf(color.Disable) > -1) {
                                            self.addDisableState(item[rowIdName], item[columnKeyName]);
                                        }
                                        return item[columnKeyName];
                                    });
                                });
                            };
                            CellFormatter.prototype.setTextColorsTableMap = function (features) {
                                var _this = this;
                                if (uk.util.isNullOrUndefined(this.textColorFeatureDef))
                                    return;
                                var rowIdName = this.textColorFeatureDef.rowId;
                                var columnKeyName = this.textColorFeatureDef.columnKey;
                                var colorName = this.textColorFeatureDef.color;
                                var colorsTable = this.textColorFeatureDef.colorsTable;
                                this.textColorsTable = _.groupBy(colorsTable, rowIdName);
                                _.forEach(this.textColorsTable, function (value, key) {
                                    _this.textColorsTable[key] = _.groupBy(_this.textColorsTable[key], function (item) {
                                        return item[columnKeyName];
                                    });
                                });
                            };
                            CellFormatter.prototype.setTextStylesTableMap = function () {
                                var _this = this;
                                if (uk.util.isNullOrUndefined(this.textStyleFeatureDef))
                                    return;
                                var rowIdName = this.textStyleFeatureDef.rowId;
                                var columnKeyName = this.textStyleFeatureDef.columnKey;
                                var styleName = this.textStyleFeatureDef.style;
                                var stylesTable = this.textStyleFeatureDef.styles;
                                this.textStylesTable = _.groupBy(stylesTable, rowIdName);
                                _.forEach(this.textStylesTable, function (value, key) {
                                    _this.textStylesTable[key] = _.groupBy(_this.textStylesTable[key], columnKeyName);
                                });
                            };
                            CellFormatter.prototype.format = function (column, notTb) {
                                var self = this;
                                if (uk.util.isNullOrUndefined(this.cellStateFeatureDef)
                                    || column.formatter !== undefined)
                                    return column;
                                var rowIdName = this.cellStateFeatureDef.rowId;
                                var columnKeyName = this.cellStateFeatureDef.columnKey;
                                var stateName = this.cellStateFeatureDef.state;
                                var statesTable = this.cellStateFeatureDef.states;
                                column.formatter = function (value, rowObj) {
                                    if (uk.util.isNullOrUndefined(rowObj))
                                        return value;
                                    var origValue = value;
                                    if (!notTb && column.constraint) {
                                        var constraint = column.constraint;
                                        var valueType = constraint.primitiveValue ? ui.validation.getConstraint(constraint.primitiveValue).valueType
                                            : constraint.cDisplayType;
                                        if (!uk.util.isNullOrUndefined(value) && !_.isEmpty(value)) {
                                            if (valueType === "TimeWithDay") {
                                                var minutes = uk.time.minutesBased.clock.dayattr.parseString(value).asMinutes;
                                                var timeOpts = { timeWithDay: true };
                                                var formatter = new uk.text.TimeWithDayFormatter(timeOpts);
                                                if (!uk.util.isNullOrUndefined(minutes)) {
                                                    try {
                                                        value = formatter.format(minutes);
                                                    }
                                                    catch (e) { }
                                                }
                                            }
                                            else if (valueType === "Clock") {
                                                var minutes = uk.time.minutesBased.clock.dayattr.parseString(value).asMinutes;
                                                var timeOpts = { timeWithDay: false };
                                                var formatter = new uk.text.TimeWithDayFormatter(timeOpts);
                                                if (!uk.util.isNullOrUndefined(minutes)) {
                                                    try {
                                                        value = formatter.format(minutes);
                                                    }
                                                    catch (e) { }
                                                }
                                            }
                                            else if (valueType === "Currency") {
                                                var currencyOpts = new ui.option.CurrencyEditorOption();
                                                currencyOpts.grouplength = constraint.groupLength | 3;
                                                currencyOpts.decimallength = constraint.decimalLength | 2;
                                                currencyOpts.currencyformat = constraint.currencyFormat ? constraint.currencyFormat : "JPY";
                                                var groupSeparator = constraint.groupSeparator || ",";
                                                var rawValue = uk.text.replaceAll(value, groupSeparator, "");
                                                var formatter = new uk.text.NumberFormatter({ option: currencyOpts });
                                                var numVal = Number(rawValue);
                                                if (!isNaN(numVal))
                                                    value = formatter.format(numVal);
                                                else
                                                    value = rawValue;
                                            }
                                        }
                                    }
                                    var _self = self;
                                    setTimeout(function () {
                                        var $gridCell = internal.getCellById(self.$grid, rowObj[self.$grid.igGrid("option", "primaryKey")], column.key);
                                        if (!$gridCell)
                                            return;
                                        $gridCell.data(internal.CELL_ORIG_VAL, origValue);
                                        var $tr = $gridCell.closest("tr");
                                        var cell = {
                                            columnKey: column.key,
                                            element: $gridCell[0],
                                            rowIndex: $tr.data("rowIdx"),
                                            id: $tr.data("id")
                                        };
                                        errors.markIfError(self.$grid, cell);
                                        color.markIfEdit(self.$grid, cell);
                                        if (!uk.util.isNullOrUndefined(self.disableRows)) {
                                            var disableRow = self.disableRows[cell.id];
                                            if (!uk.util.isNullOrUndefined(disableRow) && disableRow.length > 0 && disableRow[0].disable) {
                                                $gridCell.addClass(color.Disable);
                                            }
                                        }
                                        if (!uk.util.isNullOrUndefined(self.rowStates) && !uk.util.isNullOrUndefined(rowIdName)
                                            && !uk.util.isNullOrUndefined(columnKeyName) && !uk.util.isNullOrUndefined(stateName)
                                            && !uk.util.isNullOrUndefined(self.rowStates[cell.id])) {
                                            var cellState = self.rowStates[cell.id][column.key];
                                            if (uk.util.isNullOrUndefined(cellState) || cellState.length === 0)
                                                return;
                                            _.forEach(cellState[0][stateName], function (stt) {
                                                $gridCell.addClass(stt);
                                            });
                                        }
                                    }, 0);
                                    return value;
                                };
                                return column;
                            };
                            CellFormatter.prototype.addDisableState = function (id, key) {
                                var self = this;
                                var cbSelect = self.$grid.data(internal.CB_SELECTED);
                                if (!cbSelect) {
                                    cbSelect = {};
                                    self.$grid.data(internal.CB_SELECTED, cbSelect);
                                }
                                var cbColConf = cbSelect[key];
                                if (!cbColConf) {
                                    var ds = new Set();
                                    ds.add(id);
                                    cbSelect[key] = { disableRows: ds };
                                    return;
                                }
                                if (!cbColConf.disableRows) {
                                    cbColConf.disableRows = new Set();
                                }
                                cbColConf.disableRows.add(id);
                            };
                            CellFormatter.prototype.style = function ($grid, cell) {
                                var self = this;
                                if (uk.util.isNullOrUndefined(this.cellStateFeatureDef))
                                    return;
                                var rowIdName = this.cellStateFeatureDef.rowId;
                                var columnKeyName = this.cellStateFeatureDef.columnKey;
                                var stateName = this.cellStateFeatureDef.state;
                                var statesTable = this.cellStateFeatureDef.states;
                                var controlType = utils.getControlType($grid, cell.columnKey);
                                if (!uk.util.isNullOrUndefined(this.disableRows)) {
                                    var disableRow = this.disableRows[cell.id];
                                    if (!uk.util.isNullOrUndefined(disableRow) && disableRow.length > 0 && disableRow[0].disable) {
                                        cell.$element.addClass(color.Disable);
                                        utils.disableNtsControl($grid, cell, controlType);
                                    }
                                }
                                if (!uk.util.isNullOrUndefined(self.rowStates) && !uk.util.isNullOrUndefined(rowIdName)
                                    && !uk.util.isNullOrUndefined(columnKeyName) && !uk.util.isNullOrUndefined(stateName)
                                    && !uk.util.isNullOrUndefined(self.rowStates[cell.id])) {
                                    var cellState = self.rowStates[cell.id][cell.columnKey];
                                    if (uk.util.isNullOrUndefined(cellState) || cellState.length === 0)
                                        return;
                                    _.forEach(cellState[0][stateName], function (stt) {
                                        if (stt === color.Disable && !cell.$element.hasClass(color.Disable)) {
                                            utils.disableNtsControl($grid, cell, controlType);
                                        }
                                        cell.$element.addClass(stt);
                                    });
                                }
                            };
                            CellFormatter.prototype.setTextColor = function ($grid, cell) {
                                if (uk.util.isNullOrUndefined(this.textColorFeatureDef))
                                    return;
                                var rowIdName = this.textColorFeatureDef.rowId;
                                var columnKeyName = this.textColorFeatureDef.columnKey;
                                var colorName = this.textColorFeatureDef.color;
                                var colorsTable = this.textColorFeatureDef.colorsTable;
                                if (!uk.util.isNullOrUndefined(colorsTable) && !uk.util.isNullOrUndefined(rowIdName)
                                    && !uk.util.isNullOrUndefined(columnKeyName) && !uk.util.isNullOrUndefined(colorName)
                                    && !uk.util.isNullOrUndefined(this.textColorsTable[cell.id])) {
                                    var textColor = this.textColorsTable[cell.id][cell.columnKey];
                                    if (uk.util.isNullOrUndefined(textColor) || textColor.length === 0)
                                        return;
                                    var txtColor = textColor[0][colorName];
                                    if (txtColor.indexOf("#") === 0) {
                                        cell.$element.css("color", txtColor);
                                        return;
                                    }
                                    cell.$element.addClass(txtColor);
                                }
                            };
                            CellFormatter.prototype.setTextStyle = function ($grid, cell) {
                                if (uk.util.isNullOrUndefined(this.textStyleFeatureDef))
                                    return;
                                var rowIdName = this.textStyleFeatureDef.rowId;
                                var columnKeyName = this.textStyleFeatureDef.columnKey;
                                var styleName = this.textStyleFeatureDef.style;
                                var stylesTable = this.textStyleFeatureDef.styles;
                                if (!uk.util.isNullOrUndefined(stylesTable) && !uk.util.isNullOrUndefined(rowIdName)
                                    && !uk.util.isNullOrUndefined(columnKeyName) && !uk.util.isNullOrUndefined(styleName)
                                    && !uk.util.isNullOrUndefined(this.textStylesTable[cell.id])) {
                                    var textStyle = this.textStylesTable[cell.id][cell.columnKey];
                                    if (uk.util.isNullOrUndefined(textStyle) || textStyle.length === 0)
                                        return;
                                    var txtStyle = textStyle[0][styleName];
                                    cell.$element.addClass(txtStyle);
                                }
                            };
                            return CellFormatter;
                        }());
                        color.CellFormatter = CellFormatter;
                        function styleHeaders($grid, options) {
                            var headerStyles = feature.find(options.ntsFeatures, feature.HEADER_STYLES);
                            if (uk.util.isNullOrUndefined(headerStyles))
                                return;
                            setHeadersColor($grid, headerStyles.columns);
                        }
                        color.styleHeaders = styleHeaders;
                        function setHeadersColor($grid, columns) {
                            var headersTable = $grid.igGrid("headersTable");
                            var fixedHeadersTable = $grid.igGrid("fixedHeadersTable");
                            fixedHeadersTable.find("th").each(function () {
                                var $self = $(this);
                                var columnId = $self.attr("id");
                                if (uk.util.isNullOrUndefined(columnId)) {
                                    var owns = $self.attr("aria-owns");
                                    if (!owns)
                                        return;
                                    var key_1 = owns.split(" ")[0].split("_")[1];
                                    setBackground($self, key_1, columns);
                                    return;
                                }
                                var key = columnId.split("_")[1];
                                setBackground($self, key, columns);
                            });
                            headersTable.find("th").each(function () {
                                var $self = $(this);
                                var columnId = $self.attr("id");
                                if (uk.util.isNullOrUndefined(columnId)) {
                                    var owns = $self.attr("aria-owns");
                                    if (!owns)
                                        return;
                                    var key_2 = owns.split(" ")[0].split("_")[1];
                                    setBackground($self, key_2, columns);
                                    return;
                                }
                                var key = columnId.split("_")[1];
                                setBackground($self, key, columns);
                            });
                        }
                        function setBackground($cell, key, columns) {
                            var targetColumn;
                            _.forEach(columns, function (col) {
                                if (col.key === key) {
                                    targetColumn = col;
                                    return false;
                                }
                            });
                            if (!uk.util.isNullOrUndefined(targetColumn)) {
                                if (targetColumn.color.indexOf("#") === 0) {
                                    $cell.css("background-color", targetColumn.color);
                                    return;
                                }
                                $cell.addClass(targetColumn.color);
                            }
                        }
                        function rememberDisabled($grid, cell) {
                            var settings = $grid.data(internal.SETTINGS);
                            if (!settings)
                                return;
                            var disables = settings.disables;
                            if (!disables)
                                return;
                            var controlType = utils.getControlType($grid, cell.columnKey);
                            var row = disables[cell.id];
                            if (!row)
                                return;
                            row.forEach(function (c, i) {
                                if (c === cell.columnKey) {
                                    utils.disableNtsControl($grid, cell, controlType);
                                    cell.$element.addClass(color.Disable);
                                    return false;
                                }
                            });
                        }
                        color.rememberDisabled = rememberDisabled;
                        function pushDisable($grid, cell) {
                            var settings = $grid.data(internal.SETTINGS);
                            if (!settings)
                                return;
                            var disables = settings.disables;
                            if (!disables) {
                                settings.disables = {};
                            }
                            if (!settings.disables[cell.id] || settings.disables[cell.id].size === 0) {
                                var dset = new Set();
                                dset.add(cell.columnKey);
                                settings.disables[cell.id] = dset;
                                return;
                            }
                            settings.disables[cell.id].add(cell.columnKey);
                        }
                        color.pushDisable = pushDisable;
                        function popDisable($grid, cell) {
                            var settings = $grid.data(internal.SETTINGS);
                            if (!settings)
                                return;
                            var disables = settings.disables;
                            if (!disables || !disables[cell.id] || disables[cell.id].size === 0)
                                return;
                            disables[cell.id].delete(cell.columnKey);
                        }
                        color.popDisable = popDisable;
                        function markIfEdit($grid, cell) {
                            var targetEdits = $grid.data(internal.TARGET_EDITS);
                            var cols;
                            if (!targetEdits || !(cols = targetEdits[cell.id])) {
                                markIfOtherEdit($grid, cell);
                                return;
                            }
                            if (cols.some(function (c) {
                                return c === cell.columnKey;
                            })) {
                                cell.element.classList.add(color.ManualEditTarget);
                            }
                            else
                                markIfOtherEdit($grid, cell);
                        }
                        color.markIfEdit = markIfEdit;
                        function markIfOtherEdit($grid, cell) {
                            var otherEdits = $grid.data(internal.OTHER_EDITS);
                            var cols;
                            if (!otherEdits || !(cols = otherEdits[cell.id]))
                                return;
                            if (cols.some(function (c) {
                                return c === cell.columnKey;
                            })) {
                                cell.element.classList.add(color.ManualEditOther);
                            }
                        }
                    })(color = ntsGrid.color || (ntsGrid.color = {}));
                    var fixedColumns;
                    (function (fixedColumns) {
                        function getFixedTable($grid) {
                            return $("#" + $grid.attr("id") + "_fixed");
                        }
                        fixedColumns.getFixedTable = getFixedTable;
                        function realGridOf($grid) {
                            if (utils.isIgGrid($grid))
                                return $grid;
                            var gridId = $grid.attr("id");
                            if (uk.util.isNullOrUndefined(gridId))
                                return;
                            var endIdx = gridId.indexOf("_fixed");
                            if (endIdx !== -1) {
                                var referGrid = $("#" + gridId.substring(0, endIdx));
                                if (!uk.util.isNullOrUndefined(referGrid) && utils.fixable(referGrid))
                                    return referGrid;
                            }
                        }
                        fixedColumns.realGridOf = realGridOf;
                    })(fixedColumns || (fixedColumns = {}));
                    var sheet;
                    (function (sheet_1) {
                        var normalStyles = { backgroundColor: '', color: '' };
                        var selectedStyles = { backgroundColor: '#00B050', color: '#fff' };
                        var Configurator = (function () {
                            function Configurator(currentSheet, sheets) {
                                this.currentSheet = currentSheet;
                                this.sheets = sheets;
                                this.columnsInSheetImme = {};
                                this.columnsInSheet = {};
                            }
                            Configurator.load = function ($grid, sheetFeature) {
                                var sheetConfig = $grid.data(internal.SHEETS);
                                if (uk.util.isNullOrUndefined(sheetConfig)) {
                                    var config = new Configurator(sheetFeature.initialDisplay, sheetFeature.sheets);
                                    $grid.data(internal.SHEETS, config);
                                }
                            };
                            return Configurator;
                        }());
                        sheet_1.Configurator = Configurator;
                        function onScroll($grid) {
                            var $scrollContainer = $("#" + $grid.attr("id") + "_scrollContainer");
                            var $displayContainer = $("#" + $grid.attr("id") + "_displayContainer");
                            if ($scrollContainer.length === 0 || $displayContainer.length === 0)
                                return;
                            var scrollListener = function (evt) {
                                var sheetConfig = $grid.data(internal.SHEETS);
                                if (uk.util.isNullOrUndefined(sheetConfig))
                                    return;
                                sheetConfig.currentPosition = $scrollContainer.scrollTop() + "px";
                                sheetConfig.displayScrollTop = $displayContainer.scrollTop();
                                sheetConfig.blockId = $grid.find("tbody tr:first").data("id");
                            };
                            $scrollContainer.on(events.Handler.SCROLL, scrollListener);
                        }
                        sheet_1.onScroll = onScroll;
                        function setup($grid, options) {
                            var sheetFeature = feature.find(options.ntsFeatures, feature.SHEET);
                            if (uk.util.isNullOrUndefined(sheetFeature))
                                return;
                            var hidingFeature = { name: 'Hiding' };
                            if (feature.isEnable(options.features, feature.HIDING)) {
                                feature.replaceBy(options, feature.HIDING, hidingFeature);
                            }
                            else {
                                options.features.push(hidingFeature);
                            }
                            Configurator.load($grid, sheetFeature);
                            configButtons($grid, sheetFeature.sheets);
                        }
                        sheet_1.setup = setup;
                        function configButtons($grid, sheets) {
                            var gridWrapper = $("<div class='nts-grid-wrapper'/>");
                            $grid.wrap($("<div class='nts-grid-container'/>").css("visibility", "hidden").wrap(gridWrapper));
                            var gridContainer = $grid.closest(".nts-grid-container");
                            var sheetButtonsWrapper = $("<div class='nts-grid-sheet-buttons'/>").appendTo(gridContainer);
                            var sheetMng = $grid.data(internal.SHEETS);
                            _.forEach(sheets, function (sheet) {
                                var btn = $("<button/>").addClass(sheet.name).text(sheet.text).appendTo(sheetButtonsWrapper);
                                if (sheetMng.currentSheet === sheet.name)
                                    btn.css(selectedStyles);
                                btn.on("click", function (evt) {
                                    if (!utils.hidable($grid) || utils.isErrorStatus($grid))
                                        return;
                                    updateCurrentSheet($grid, sheet.name);
                                    utils.showColumns($grid, sheet.columns);
                                    hideOthers($grid);
                                    sheetButtonsWrapper.find("button").css(normalStyles);
                                    $(this).css(selectedStyles);
                                });
                            });
                        }
                        function hideOthers($grid) {
                            var sheetMng = $grid.data(internal.SHEETS);
                            if (uk.util.isNullOrUndefined(sheetMng))
                                return;
                            var displayColumns;
                            _.forEach(sheetMng.sheets, function (sheet) {
                                if (sheet.name !== sheetMng.currentSheet) {
                                    utils.hideColumns($grid, sheet.columns);
                                }
                                else {
                                    displayColumns = sheet.columns;
                                }
                            });
                            setTimeout(function () {
                                _.forEach(displayColumns, function (column) {
                                    columnSize.loadOne($grid, column);
                                });
                            }, 0);
                        }
                        sheet_1.hideOthers = hideOthers;
                        function updateCurrentSheet($grid, name) {
                            var sheetMng = $grid.data(internal.SHEETS);
                            if (uk.util.isNullOrUndefined(sheetMng))
                                return;
                            sheetMng.currentSheet = name;
                            $grid.data(internal.SHEETS, sheetMng);
                        }
                        var load;
                        (function (load) {
                            function setup($grid, options) {
                                var sheetFeature = feature.find(options.ntsFeatures, feature.SHEET);
                                if (uk.util.isNullOrUndefined(sheetFeature)) {
                                    var idxes_1 = {};
                                    utils.analyzeColumns(options.columns)
                                        .filter(function (c) { return c.hidden !== true; })
                                        .forEach(function (c, i) {
                                        idxes_1[c.key] = i;
                                    });
                                    var setting = $grid.data(internal.SETTINGS);
                                    if (!setting.descriptor) {
                                        setting.descriptor = new settings.Descriptor();
                                    }
                                    setting.descriptor.colIdxes = idxes_1;
                                    if (uk.util.isNullOrUndefined($grid.data(internal.GRID_OPTIONS))) {
                                        $grid.data(internal.GRID_OPTIONS, _.cloneDeep(options));
                                    }
                                    return;
                                }
                                Configurator.load($grid, sheetFeature);
                                configButtons($grid, sheetFeature.sheets);
                                if (!uk.util.isNullOrUndefined($grid.data(internal.GRID_OPTIONS)))
                                    return;
                                $grid.data(internal.GRID_OPTIONS, _.cloneDeep(options));
                                var sheetMng = $grid.data(internal.SHEETS);
                                var sheet = _.filter(sheetMng.sheets, function (sheet) {
                                    return sheet.name === sheetMng.currentSheet;
                                });
                                var columns;
                                if (!sheetMng.sheetColumns) {
                                    sheetMng.sheetColumns = {};
                                }
                                columns = sheetMng.sheetColumns[sheet[0].name];
                                if (!columns) {
                                    columns = getSheetColumns(options.columns, sheet[0], options.features, sheetMng);
                                    sheetMng.sheetColumns[sheet[0].name] = columns.all;
                                    var idxes_2 = {};
                                    utils.analyzeColumns(columns.unfixed)
                                        .filter(function (c) { return c.hidden !== true; })
                                        .forEach(function (c, i) {
                                        idxes_2[c.key] = i;
                                    });
                                    var setting = $grid.data(internal.SETTINGS);
                                    if (!setting.descriptor) {
                                        setting.descriptor = new settings.Descriptor();
                                        setting.descriptor.fixedColumns = columns.fixed;
                                    }
                                    setting.descriptor.colIdxes = idxes_2;
                                    options.columns = columns.all;
                                }
                                else
                                    options.columns = columns;
                            }
                            load.setup = setup;
                            function configButtons($grid, sheets) {
                                if ($grid.closest(".nts-grid-container").length > 0)
                                    return;
                                $grid.closest(".nts-grid-wrapper").wrap($("<div class='nts-grid-container'/>"));
                                var gridContainer = $grid.closest(".nts-grid-container");
                                var sheetButtonsWrapper = $("<div class='nts-grid-sheet-buttons'/>").appendTo(gridContainer);
                                var sheetMng = $grid.data(internal.SHEETS);
                                _.forEach(sheets, function (sheet) {
                                    var btn = $("<button/>").addClass(sheet.name).text(sheet.text).appendTo(sheetButtonsWrapper);
                                    if (sheetMng.currentSheet === sheet.name)
                                        btn.css(selectedStyles);
                                    btn.on("click", function (evt) {
                                        if (utils.isErrorStatus($grid))
                                            return;
                                        updateCurrentSheet($grid, sheet.name);
                                        var options = $grid.data(internal.GRID_OPTIONS);
                                        var columns, clonedColumns;
                                        if (!sheetMng.sheetColumns) {
                                            sheetMng.sheetColumns = {};
                                        }
                                        var settings = $grid.data(internal.SETTINGS);
                                        columns = sheetMng.sheetColumns[sheet.name];
                                        if (!columns) {
                                            columns = getSheetColumns(options.columns, sheet, options.features, sheetMng);
                                            sheetMng.sheetColumns[sheet.name] = columns.all;
                                            var idxes_3 = {};
                                            utils.analyzeColumns(columns.unfixed)
                                                .filter(function (c) { return c.hidden !== true; })
                                                .forEach(function (c, i) {
                                                idxes_3[c.key] = i;
                                            });
                                            settings.descriptor.colIdxes = idxes_3;
                                            clonedColumns = columns.all;
                                        }
                                        else {
                                            var idxes_4 = {};
                                            var fixedColumns_3 = settings.descriptor.fixedColumns;
                                            if (fixedColumns_3) {
                                                var unfixed = columns.slice(fixedColumns_3.length);
                                                utils.analyzeColumns(unfixed)
                                                    .filter(function (c) { return c.hidden !== true; })
                                                    .forEach(function (c, i) {
                                                    idxes_4[c.key] = i;
                                                });
                                                settings.descriptor.colIdxes = idxes_4;
                                            }
                                            clonedColumns = columns;
                                        }
                                        var clonedOpts = _.cloneDeep(options);
                                        clonedOpts.columns = clonedColumns;
                                        clonedOpts.dataSource = $grid.igGrid("option", "dataSource");
                                        $grid.igGrid("destroy");
                                        $grid.off();
                                        var pagingFt = feature.find(clonedOpts.features, feature.PAGING);
                                        if (pagingFt && settings) {
                                            if (!uk.util.isNullOrUndefined(settings.pageIndex)) {
                                                pagingFt.currentPageIndex = settings.pageIndex;
                                            }
                                            if (!uk.util.isNullOrUndefined(settings.pageSize)) {
                                                pagingFt.pageSize = settings.pageSize;
                                            }
                                            feature.replaceBy(clonedOpts, feature.PAGING, pagingFt);
                                        }
                                        $grid.ntsGrid(clonedOpts);
                                        sheetButtonsWrapper.find("button").css(normalStyles);
                                        $(this).css(selectedStyles);
                                    });
                                });
                            }
                            function getSheetColumns(allColumns, displaySheet, features, sheetMng) {
                                var fixedColumns = [];
                                var columns = [];
                                _.forEach(allColumns, function (column) {
                                    var index;
                                    if (column.group !== undefined && _.find(displaySheet.columns, function (col, i) {
                                        if (col === column.group[0].key) {
                                            index = i;
                                            return true;
                                        }
                                    }) !== undefined) {
                                        columns[index] = column;
                                        column.group.forEach(function (sc) {
                                            if (!sheetMng.columnsInSheetImme[sc.key]) {
                                                var mSet = new Set();
                                                mSet.add(displaySheet.name);
                                                sheetMng.columnsInSheetImme[sc.key] = mSet;
                                            }
                                            else {
                                                sheetMng.columnsInSheetImme[sc.key].add(displaySheet.name);
                                            }
                                        });
                                        return;
                                    }
                                    var belongToSheet = _.find(displaySheet.columns, function (col, i) {
                                        if (col === column.key) {
                                            index = i;
                                            return true;
                                        }
                                    }) !== undefined;
                                    if (belongToSheet) {
                                        columns[index] = column;
                                        if (!sheetMng.columnsInSheetImme[column.key]) {
                                            var mSet = new Set();
                                            mSet.add(displaySheet.name);
                                            sheetMng.columnsInSheetImme[column.key] = mSet;
                                        }
                                        else {
                                            sheetMng.columnsInSheetImme[column.key].add(displaySheet.name);
                                        }
                                        return;
                                    }
                                    var columnFixFeature = feature.find(features, feature.COLUMN_FIX);
                                    if (!uk.util.isNullOrUndefined(columnFixFeature)) {
                                        var isFixed = _.find(columnFixFeature.columnSettings, function (s) {
                                            return s.columnKey === column.key;
                                        }) !== undefined;
                                        if (isFixed) {
                                            fixedColumns.push(column);
                                            return;
                                        }
                                    }
                                });
                                _.remove(columns, function (c) { return uk.util.isNullOrUndefined(c); });
                                return { fixed: fixedColumns,
                                    unfixed: columns,
                                    all: _.concat(fixedColumns, columns) };
                            }
                        })(load = sheet_1.load || (sheet_1.load = {}));
                    })(sheet || (sheet = {}));
                    var onDemand;
                    (function (onDemand) {
                        var Loader = (function () {
                            function Loader(allKeysPath, pageRecordsPath) {
                                this.allKeysPath = allKeysPath;
                                this.pageRecordsPath = pageRecordsPath;
                            }
                            return Loader;
                        }());
                        onDemand.Loader = Loader;
                        function hidePageSizeDD($grid, options) {
                            if (options && !feature.find(options.ntsFeatures, feature.DEMAND_LOAD))
                                return;
                            var $gridContainer = $($grid.igGrid("container"));
                            if ($gridContainer.length > 0) {
                                $gridContainer.find("div[class*='ui-iggrid-pagesizedropdowncontainer']").hide();
                            }
                        }
                        onDemand.hidePageSizeDD = hidePageSizeDD;
                        function loadKeys($grid, path) {
                            var dfd = $.Deferred();
                            uk.request.ajax(path).done(function (keys) {
                                var loader = $grid.data(internal.LOADER);
                                if (!loader.keys || loader.keys.length === 0)
                                    loader.keys = keys;
                                dfd.resolve(loader.keys);
                            }).fail(function () {
                                dfd.reject();
                            });
                            return dfd.promise();
                        }
                        onDemand.loadKeys = loadKeys;
                        function loadLazy($grid, path, keys, startIndex, endIndex, dataSource, primaryKey) {
                            var dfd = $.Deferred();
                            uk.request.ajax(path, keys).done(function (data) {
                                var origDs = $grid.data(internal.ORIG_DS);
                                if (!origDs) {
                                    $grid.data(internal.ORIG_DS, []);
                                    origDs = $grid.data(internal.ORIG_DS);
                                }
                                var add = true;
                                if (origDs.length >= endIndex) {
                                    add = false;
                                }
                                _.forEach(data, function (rData, index) {
                                    for (var i = startIndex; i < endIndex; i++) {
                                        if (dataSource[i] && dataSource[i][primaryKey] === rData[primaryKey]) {
                                            rData = _.merge(rData, dataSource[i]);
                                            rData.loaded = true;
                                            dataSource.splice(i, 1, rData);
                                            if (add)
                                                origDs[i] = _.cloneDeep(rData);
                                        }
                                    }
                                });
                                dfd.resolve(dataSource);
                            }).fail(function () {
                                ;
                                dfd.reject();
                            });
                            return dfd.promise();
                        }
                        onDemand.loadLazy = loadLazy;
                        function initial($grid, options) {
                            if (!options)
                                return false;
                            var pagingFt = feature.find(options.features, feature.PAGING);
                            if (!pagingFt)
                                return false;
                            bindPageChange($grid);
                            var setting = $grid.data(internal.SETTINGS);
                            if (uk.util.isNullOrUndefined(setting.pageSize)) {
                                setting.pageSize = pagingFt.pageSize;
                            }
                            var demandLoadFt = feature.find(options.ntsFeatures, feature.DEMAND_LOAD);
                            if (!demandLoadFt)
                                return false;
                            var pageSize = pagingFt.pageSize;
                            var loader = $grid.data(internal.LOADER);
                            if (!loader) {
                                $grid.data(internal.LOADER, new Loader(demandLoadFt.allKeysPath, demandLoadFt.pageRecordsPath));
                            }
                            else if (loader.keys) {
                                pageSize = setting.pageSize;
                                return false;
                            }
                            var bindKeys = function (keys) {
                                var primaryKey = options.primaryKey;
                                var ds = keys.map(function (key, index) {
                                    var obj = {};
                                    obj[primaryKey] = key;
                                    obj["loaded"] = false;
                                    return obj;
                                });
                                var firstRecordIndex = (pagingFt.currentPageIndex || 0) * pageSize;
                                var lastRecordIndex = firstRecordIndex + pageSize;
                                var firstPageItems = keys.slice(firstRecordIndex, lastRecordIndex);
                                loadLazy($grid, demandLoadFt.pageRecordsPath, firstPageItems, firstRecordIndex, lastRecordIndex, ds, primaryKey).done(function (data) {
                                    options.dataSource = options.dataSourceAdapter ? options.dataSourceAdapter(data) : data;
                                    $grid.igGrid(options);
                                });
                            };
                            if (options.recordKeys && options.recordKeys.constructor === Array) {
                                loader = $grid.data(internal.LOADER);
                                loader.keys = options.recordKeys;
                                bindKeys(options.recordKeys);
                                return true;
                            }
                            loadKeys($grid, demandLoadFt.allKeysPath).done(function (keys) {
                                bindKeys(keys);
                            }).fail(function () {
                            });
                            return true;
                        }
                        onDemand.initial = initial;
                        function bindPageChange($grid) {
                            $grid.on(events.Handler.PAGE_INDEX_CHANGE, function (evt, ui) {
                                var newPageIndex = ui.newPageIndex;
                                var pageSize = ui.owner.pageSize();
                                var startIndex = newPageIndex * pageSize;
                                var endIndex = startIndex + pageSize;
                                var settings = $grid.data(internal.SETTINGS);
                                settings.pageChanged = true;
                                settings.pageIndex = ui.newPageIndex;
                                var loader = $grid.data(internal.LOADER);
                                if (!loader || !loader.keys)
                                    return;
                                var dataSource = $grid.igGrid("option", "dataSource");
                                var primaryKey = $grid.igGrid("option", "primaryKey");
                                var newKeys = loader.keys.slice(startIndex, endIndex);
                                for (var i = endIndex - 1; i >= startIndex; i--) {
                                    if (dataSource[i] && dataSource[i].loaded) {
                                        newKeys.splice(i - startIndex, 1);
                                    }
                                }
                                if (newKeys.length === 0)
                                    return;
                                loadLazy($grid, loader.pageRecordsPath, newKeys, startIndex, endIndex, dataSource, primaryKey).done(function (data) {
                                    var ds = settings.dataSourceAdapter ? settings.dataSourceAdapter(data) : data;
                                    $grid.igGrid("option", "dataSource", ds);
                                    ui.owner.pageIndex(ui.newPageIndex);
                                });
                                return false;
                            });
                            $grid.on(events.Handler.PAGE_SIZE_CHANGE, function (evt, ui) {
                                var setting = $grid.data(internal.SETTINGS);
                                setting.pageSize = ui.newPageSize;
                                setting.pageIndex = 0;
                                var loader = $grid.data(internal.LOADER);
                                if (!loader)
                                    return;
                                var currentPageIndex = 0;
                                var startIndex = currentPageIndex * ui.newPageSize;
                                var endIndex = startIndex + ui.newPageSize;
                                var newKeys = loader.keys.slice(startIndex, endIndex);
                                var dataSource = $grid.igGrid("option", "dataSource");
                                var primaryKey = $grid.igGrid("option", "primaryKey");
                                for (var i = endIndex - 1; i >= startIndex; i--) {
                                    if (dataSource[i] && dataSource[i].loaded) {
                                        newKeys.splice(i - startIndex, 1);
                                    }
                                }
                                if (newKeys.length === 0)
                                    return;
                                loadLazy($grid, loader.pageRecordsPath, newKeys, startIndex, endIndex, dataSource, primaryKey).done(function (data) {
                                    var ds = setting.dataSourceAdapter ? setting.dataSourceAdapter(data) : data;
                                    $grid.igGrid("option", "dataSource", ds);
                                    ui.owner.pageSize(ui.newPageSize);
                                });
                                return false;
                            });
                        }
                    })(onDemand || (onDemand = {}));
                    var settings;
                    (function (settings) {
                        settings.USER_M = "M";
                        settings.USER_O = "O";
                        var Descriptor = (function () {
                            function Descriptor(startRow, rowCount, elements, keyIdxes) {
                                this.startRow = startRow;
                                this.rowCount = rowCount;
                                this.elements = elements;
                                this.keyIdxes = keyIdxes;
                            }
                            Descriptor.prototype.update = function (startRow, rowCount, elements) {
                                this.startRow = startRow;
                                this.rowCount = rowCount;
                                this.elements = elements;
                            };
                            Descriptor.prototype.isFixedColumn = function (column) {
                                var index;
                                _.forEach(this.fixedColumns, function (c, i) {
                                    if (c.key === column) {
                                        index = i;
                                        return false;
                                    }
                                });
                                return index;
                            };
                            return Descriptor;
                        }());
                        settings.Descriptor = Descriptor;
                        function build($grid, options) {
                            var data = {};
                            var rebuild;
                            data.preventEditInError = options.preventEditInError;
                            data.dataSourceAdapter = options.dataSourceAdapter;
                            data.errorColumns = options.errorColumns;
                            data.errorsOnPage = options.showErrorsOnPage;
                            if (!$grid.data(internal.SETTINGS)) {
                                $grid.data(internal.SETTINGS, data);
                            }
                            else {
                                rebuild = true;
                            }
                            $grid.on(events.Handler.RECORDS, function (evt, arg) {
                                if (uk.util.isNullOrUndefined(arg.owner._startRowIndex)) {
                                    arg.owner._startRowIndex = 0;
                                }
                                var setting = $grid.data(internal.SETTINGS);
                                var owner = arg.owner;
                                var pageIndex = 0, pageSize = 0;
                                if (!uk.util.isNullOrUndefined(setting.pageIndex)) {
                                    pageIndex = setting.pageIndex;
                                }
                                if (!uk.util.isNullOrUndefined(setting.pageSize)) {
                                    pageSize = setting.pageSize;
                                }
                                var startRow = owner._startRowIndex + pageIndex * pageSize;
                                if (setting.pageChanged) {
                                    startRow = pageIndex * pageSize;
                                    setTimeout(function () {
                                        setting.pageChanged = false;
                                    }, 0);
                                }
                                if (!setting.descriptor) {
                                    var pk_1 = owner.dataSource.settings.primaryKey;
                                    var keyIdxes_1 = {};
                                    owner.dataSource._origDs.forEach(function (d, i) {
                                        keyIdxes_1[d[pk_1]] = i;
                                    });
                                    var descriptor = new Descriptor(startRow, owner._virtualRowCount, owner._virtualDom, keyIdxes_1);
                                    setting.descriptor = descriptor;
                                    setting.descriptor.fixedColumns = owner._fixedColumns;
                                    setting.descriptor.fixedTable = owner._fixedTable;
                                    setting.descriptor.headerCells = owner._headerCells;
                                    setting.descriptor.headerParent = owner._headerParent;
                                    return;
                                }
                                setting.descriptor.update(startRow, owner._virtualRowCount, owner._virtualDom);
                                if (!setting.descriptor.keyIdxes || $grid.data("ntsRowDeleting")) {
                                    var pk_2 = owner.dataSource.settings.primaryKey;
                                    var keyIdxes_2 = {};
                                    if (owner.dataSource._origDs) {
                                        owner.dataSource._origDs.forEach(function (d, i) {
                                            keyIdxes_2[d[pk_2]] = i;
                                        });
                                    }
                                    setting.descriptor.keyIdxes = keyIdxes_2;
                                    setting.descriptor.fixedTable = owner._fixedTable;
                                    setting.descriptor.headerCells = owner._headerCells;
                                    setting.descriptor.headerParent = owner._headerParent;
                                    $grid.data("ntsRowDeleting", false);
                                }
                                if (rebuild) {
                                    setting.descriptor.fixedTable = owner._fixedTable;
                                    setting.descriptor.headerCells = owner._headerCells;
                                    setting.descriptor.headerParent = owner._headerParent;
                                }
                            });
                        }
                        settings.build = build;
                        function setGridSize($grid) {
                            var height = window.innerHeight;
                            var width = window.innerWidth;
                            $grid.igGrid("option", "width", width - 240);
                            $grid.igGrid("option", "height", height - 90);
                        }
                        settings.setGridSize = setGridSize;
                    })(settings || (settings = {}));
                    var internal;
                    (function (internal) {
                        internal.ORIG_DS = "ntsOrigDs";
                        internal.CONTROL_TYPES = "ntsControlTypesGroup";
                        internal.COMBO_SELECTED = "ntsComboSelection";
                        internal.CB_SELECTED = "ntsCheckboxSelection";
                        internal.UPDATED_CELLS = "ntsUpdatedCells";
                        internal.TARGET_EDITS = "ntsTargetEdits";
                        internal.OTHER_EDITS = "ntsOtherEdits";
                        internal.CELL_FORMATTER = "ntsCellFormatter";
                        internal.GRID_OPTIONS = "ntsGridOptions";
                        internal.SELECTED_CELL = "ntsSelectedCell";
                        internal.SHEETS = "ntsGridSheets";
                        internal.SPECIAL_COL_TYPES = "ntsSpecialColumnTypes";
                        internal.ENTER_DIRECT = "enter";
                        internal.SETTINGS = "ntsSettings";
                        internal.ERRORS = "ntsErrors";
                        internal.ERRORS_LOG = "ntsErrorsLog";
                        internal.LOADER = "ntsLoader";
                        internal.TXT_RAW = "rawText";
                        internal.CELL_ORIG_VAL = "_origValue";
                        function getCellById($grid, rowId, key) {
                            var settings = $grid.data(internal.SETTINGS);
                            if (!settings || !settings.descriptor)
                                return;
                            var descriptor = settings.descriptor;
                            if (!descriptor.keyIdxes || !descriptor.colIdxes)
                                return;
                            var idx = descriptor.keyIdxes[rowId];
                            var colIdx = descriptor.colIdxes[key];
                            if (uk.util.isNullOrUndefined(colIdx)) {
                                var colIdx_1 = descriptor.isFixedColumn(key);
                                if (!uk.util.isNullOrUndefined(colIdx_1)) {
                                    return descriptor.fixedTable.find("tr:eq(" + (idx - descriptor.startRow) + ") td:eq(" + colIdx_1 + ")");
                                }
                            }
                            if (!uk.util.isNullOrUndefined(idx) && idx >= descriptor.startRow
                                && idx <= descriptor.rowCount + descriptor.startRow - 1 && !uk.util.isNullOrUndefined(colIdx)) {
                                return $(descriptor.elements[idx - descriptor.startRow][colIdx]);
                            }
                            return $grid.igGrid("cellById", rowId, key);
                        }
                        internal.getCellById = getCellById;
                    })(internal || (internal = {}));
                    var utils;
                    (function (utils) {
                        function isArrowKey(evt) {
                            return evt.keyCode >= 37 && evt.keyCode <= 40;
                        }
                        utils.isArrowKey = isArrowKey;
                        function isArrowLeft(evt) {
                            return evt.keyCode === 37;
                        }
                        utils.isArrowLeft = isArrowLeft;
                        function isArrowRight(evt) {
                            return evt.keyCode === 39;
                        }
                        utils.isArrowRight = isArrowRight;
                        function isAlphaNumeric(evt) {
                            return (evt.keyCode >= 48 && evt.keyCode <= 90)
                                || (evt.keyCode >= 96 && evt.keyCode <= 105);
                        }
                        utils.isAlphaNumeric = isAlphaNumeric;
                        function isMinusSymbol(evt) {
                            return evt.keyCode === 189 || evt.keyCode === 109;
                        }
                        utils.isMinusSymbol = isMinusSymbol;
                        function isTabKey(evt) {
                            return evt.keyCode === 9;
                        }
                        utils.isTabKey = isTabKey;
                        function isEnterKey(evt) {
                            return evt.keyCode === 13;
                        }
                        utils.isEnterKey = isEnterKey;
                        function isSpaceKey(evt) {
                            return evt.keyCode === 32;
                        }
                        utils.isSpaceKey = isSpaceKey;
                        function isDeleteKey(evt) {
                            return evt.keyCode === 46;
                        }
                        utils.isDeleteKey = isDeleteKey;
                        function isPasteKey(evt) {
                            return evt.keyCode === 86;
                        }
                        utils.isPasteKey = isPasteKey;
                        function isCopyKey(evt) {
                            return evt.keyCode === 67;
                        }
                        utils.isCopyKey = isCopyKey;
                        function isCutKey(evt) {
                            return evt.keyCode === 88;
                        }
                        utils.isCutKey = isCutKey;
                        function isErrorStatus($grid) {
                            var cell = selection.getSelectedCell($grid);
                            return isEditMode($grid) && errors.any(cell);
                        }
                        utils.isErrorStatus = isErrorStatus;
                        function isNotErrorCell($grid, evt) {
                            var cell = selection.getSelectedCell($grid);
                            var $target = $(evt.target);
                            var td = $target;
                            if (!$target.prev().is("td"))
                                td = $target.closest("td");
                            return isEditMode($grid) && td.length > 0 && td[0] !== cell.element[0]
                                && errors.any(cell);
                        }
                        utils.isNotErrorCell = isNotErrorCell;
                        function isEditMode($grid) {
                            return (updatable($grid) && $grid.igGridUpdating("isEditing"));
                        }
                        utils.isEditMode = isEditMode;
                        function isIgGrid($grid) {
                            return $grid && !uk.util.isNullOrUndefined($grid.data("igGrid"));
                        }
                        utils.isIgGrid = isIgGrid;
                        function selectable($grid) {
                            return $grid && !uk.util.isNullOrUndefined($grid.data("igGridSelection"));
                        }
                        utils.selectable = selectable;
                        function updatable($grid) {
                            return $grid && !uk.util.isNullOrUndefined($grid.data("igGridUpdating"));
                        }
                        utils.updatable = updatable;
                        function fixable($grid) {
                            return $grid && !uk.util.isNullOrUndefined($grid.data("igGridColumnFixing"));
                        }
                        utils.fixable = fixable;
                        function hidable($grid) {
                            return $grid && !uk.util.isNullOrUndefined($grid.data("igGridHiding"));
                        }
                        utils.hidable = hidable;
                        function pageable($grid) {
                            return $grid && !uk.util.isNullOrUndefined($grid.data("igGridPaging"));
                        }
                        utils.pageable = pageable;
                        function disabled($cell) {
                            return $cell.hasClass(color.Disable);
                        }
                        utils.disabled = disabled;
                        function dataTypeOfPrimaryKey($grid, columnsMap) {
                            if (uk.util.isNullOrUndefined(columnsMap))
                                return;
                            var columns = columnsMap["undefined"];
                            if (Object.keys(columnsMap).length > 1) {
                                columns = _.concat(columnsMap["true"], columnsMap["undefined"]);
                            }
                            var primaryKey = $grid.igGrid("option", "primaryKey");
                            var keyColumn = _.filter(columns, function (column) {
                                return column.key === primaryKey;
                            });
                            if (!uk.util.isNullOrUndefined(keyColumn) && keyColumn.length > 0)
                                return keyColumn[0].dataType;
                            return;
                        }
                        utils.dataTypeOfPrimaryKey = dataTypeOfPrimaryKey;
                        function parseIntIfNumber(value, $grid, columnsMap) {
                            if (dataTypeOfPrimaryKey($grid, columnsMap) === "number") {
                                return parseInt(value);
                            }
                            return value;
                        }
                        utils.parseIntIfNumber = parseIntIfNumber;
                        function isCopiableControls($grid, columnKey) {
                            var columnControlTypes = $grid.data(internal.CONTROL_TYPES);
                            switch (columnControlTypes[columnKey]) {
                                case ntsControls.LINK_LABEL:
                                case ntsControls.TEXTBOX:
                                case ntsControls.LABEL:
                                    return true;
                            }
                            return false;
                        }
                        utils.isCopiableControls = isCopiableControls;
                        function isCuttableControls($grid, columnKey) {
                            var columnControlTypes = $grid.data(internal.CONTROL_TYPES);
                            switch (columnControlTypes[columnKey]) {
                                case ntsControls.TEXTBOX:
                                    return true;
                            }
                            return false;
                        }
                        utils.isCuttableControls = isCuttableControls;
                        function isPastableControls($grid, columnKey) {
                            var columnControlTypes = $grid.data(internal.CONTROL_TYPES);
                            switch (columnControlTypes[columnKey]) {
                                case ntsControls.LABEL:
                                case ntsControls.CHECKBOX:
                                case ntsControls.LINK_LABEL:
                                case ntsControls.COMBOBOX:
                                case ntsControls.FLEX_IMAGE:
                                case ntsControls.IMAGE:
                                    return false;
                            }
                            return true;
                        }
                        utils.isPastableControls = isPastableControls;
                        function isDisabled($cell) {
                            return $cell.hasClass(color.Disable);
                        }
                        utils.isDisabled = isDisabled;
                        function isComboBox($grid, columnKey) {
                            var columnControlTypes = $grid.data(internal.CONTROL_TYPES);
                            if (columnControlTypes[columnKey] === ntsControls.COMBOBOX)
                                return true;
                            return false;
                        }
                        utils.isComboBox = isComboBox;
                        function isNtsControl($grid, columnKey) {
                            var columnControlTypes = $grid.data(internal.CONTROL_TYPES);
                            switch (columnControlTypes[columnKey]) {
                                case ntsControls.LABEL:
                                case ntsControls.CHECKBOX:
                                case ntsControls.SWITCH_BUTTONS:
                                case ntsControls.COMBOBOX:
                                case ntsControls.BUTTON:
                                case ntsControls.DELETE_BUTTON:
                                case ntsControls.FLEX_IMAGE:
                                case ntsControls.IMAGE:
                                case ntsControls.TEXT_EDITOR:
                                    return true;
                            }
                            return false;
                        }
                        utils.isNtsControl = isNtsControl;
                        function getControlType($grid, columnKey) {
                            var columnControlTypes = $grid.data(internal.CONTROL_TYPES);
                            if (uk.util.isNullOrUndefined(columnControlTypes))
                                return;
                            return columnControlTypes[columnKey];
                        }
                        utils.getControlType = getControlType;
                        function comboBoxOfCell(cell) {
                            return $(cell.element).find(".nts-combo-container");
                        }
                        utils.comboBoxOfCell = comboBoxOfCell;
                        function getColumns($grid) {
                            if (isIgGrid($grid)) {
                                return $grid.igGrid("option", "columns");
                            }
                            var referGrid = fixedColumns.realGridOf($grid);
                            if (!uk.util.isNullOrUndefined(referGrid))
                                return referGrid.igGrid("option", "columns");
                        }
                        utils.getColumns = getColumns;
                        function getColumnsMap($grid) {
                            var columns = getColumns($grid);
                            return _.groupBy(columns, "fixed");
                        }
                        utils.getColumnsMap = getColumnsMap;
                        function getVisibleColumns($grid) {
                            return _.filter(getColumns($grid), function (column) {
                                return column.hidden !== true;
                            });
                        }
                        utils.getVisibleColumns = getVisibleColumns;
                        function getVisibleColumnsMap($grid) {
                            var visibleColumns = getVisibleColumns($grid);
                            return _.groupBy(visibleColumns, "fixed");
                        }
                        utils.getVisibleColumnsMap = getVisibleColumnsMap;
                        function getVisibleFixedColumns($grid) {
                            return _.filter(getColumns($grid), function (column) {
                                return column.hidden !== true && column.fixed === true;
                            });
                        }
                        utils.getVisibleFixedColumns = getVisibleFixedColumns;
                        function isFixedColumn(columnKey, visibleColumnsMap) {
                            return _.find(visibleColumnsMap["true"], function (column) {
                                return column.key === columnKey;
                            }) !== undefined;
                        }
                        utils.isFixedColumn = isFixedColumn;
                        function isFixedColumnCell(cell, visibleColumnsMap) {
                            return _.find(visibleColumnsMap["true"], function (column) {
                                return column.key === cell.columnKey;
                            }) !== undefined;
                        }
                        utils.isFixedColumnCell = isFixedColumnCell;
                        function columnsGroupOfColumn(column, visibleColumnsMap) {
                            return visibleColumnsMap[column.fixed ? "true" : "undefined"];
                        }
                        utils.columnsGroupOfColumn = columnsGroupOfColumn;
                        function columnsGroupOfCell(cell, visibleColumnsMap) {
                            if (isFixedColumnCell(cell, visibleColumnsMap))
                                return visibleColumnsMap["true"];
                            return visibleColumnsMap["undefined"];
                        }
                        utils.columnsGroupOfCell = columnsGroupOfCell;
                        function visibleColumnsFromMap(visibleColumnsMap) {
                            return _.concat(visibleColumnsMap["true"], visibleColumnsMap["undefined"]);
                        }
                        utils.visibleColumnsFromMap = visibleColumnsFromMap;
                        function noOfVisibleColumns(visibleColumnsMap) {
                            return visibleColumnsMap["true"].length + visibleColumnsMap["undefined"].length;
                        }
                        utils.noOfVisibleColumns = noOfVisibleColumns;
                        function getFixedColumns(visibleColumnsMap) {
                            return visibleColumnsMap["true"];
                        }
                        utils.getFixedColumns = getFixedColumns;
                        function getUnfixedColumns(visibleColumnsMap) {
                            return visibleColumnsMap["undefined"];
                        }
                        utils.getUnfixedColumns = getUnfixedColumns;
                        function nextColumn(visibleColumnsMap, columnIndex, isFixed) {
                            if (uk.util.isNullOrUndefined(visibleColumnsMap))
                                return;
                            var nextCol = {};
                            var mapKeyName = isFixed ? "true" : "undefined";
                            var reverseKeyName = isFixed ? "undefined" : "true";
                            if (columnIndex < visibleColumnsMap[mapKeyName].length - 1) {
                                return {
                                    options: visibleColumnsMap[mapKeyName][columnIndex + 1],
                                    index: columnIndex + 1
                                };
                            }
                            else if (columnIndex === visibleColumnsMap[mapKeyName].length - 1) {
                                return {
                                    options: visibleColumnsMap[reverseKeyName][0],
                                    index: 0
                                };
                            }
                        }
                        utils.nextColumn = nextColumn;
                        function nextColumnByKey(visibleColumnsMap, columnKey, isFixed) {
                            if (uk.util.isNullOrUndefined(visibleColumnsMap))
                                return;
                            var currentColumnIndex;
                            var currentColumn;
                            var fixedColumns = visibleColumnsMap["true"];
                            var unfixedColumns = visibleColumnsMap["undefined"];
                            if (isFixed && fixedColumns.length > 0) {
                                _.forEach(fixedColumns, function (col, index) {
                                    if (col.key === columnKey) {
                                        currentColumnIndex = index;
                                        currentColumn = col;
                                        return false;
                                    }
                                });
                                if (uk.util.isNullOrUndefined(currentColumn) || uk.util.isNullOrUndefined(currentColumnIndex))
                                    return;
                                if (currentColumnIndex === fixedColumns.length - 1) {
                                    return {
                                        options: unfixedColumns[0],
                                        index: 0
                                    };
                                }
                                return {
                                    options: fixedColumns[currentColumnIndex + 1],
                                    index: currentColumnIndex + 1
                                };
                            }
                            if (!isFixed && unfixedColumns.length > 0) {
                                _.forEach(unfixedColumns, function (col, index) {
                                    if (col.key === columnKey) {
                                        currentColumnIndex = index;
                                        currentColumn = col;
                                        return false;
                                    }
                                });
                                if (uk.util.isNullOrUndefined(currentColumn) || uk.util.isNullOrUndefined(currentColumnIndex))
                                    return;
                                if (currentColumnIndex === unfixedColumns.length - 1) {
                                    return {
                                        options: fixedColumns.length > 0 ? fixedColumns[0] : unfixedColumns[0],
                                        index: 0
                                    };
                                }
                                return {
                                    options: unfixedColumns[currentColumnIndex + 1],
                                    index: currentColumnIndex + 1
                                };
                            }
                        }
                        utils.nextColumnByKey = nextColumnByKey;
                        function rowAt(cell) {
                            if (uk.util.isNullOrUndefined(cell))
                                return;
                            return $(cell.element).closest("tr");
                        }
                        utils.rowAt = rowAt;
                        function nextNRow(cell, noOfNext) {
                            return $(cell.element).closest("tr").nextAll("tr:eq(" + (noOfNext - 1) + ")");
                        }
                        utils.nextNRow = nextNRow;
                        function getDisplayColumnIndex($grid, cell) {
                            var columns = $grid.igGrid("option", "columns");
                            for (var i = 0; i < columns.length; i++) {
                                if (columns[i].key === cell.columnKey)
                                    return i;
                            }
                            return -1;
                        }
                        utils.getDisplayColumnIndex = getDisplayColumnIndex;
                        function getDisplayContainer($grid) {
                            return $("#" + $grid.attr("id") + "_displayContainer");
                        }
                        utils.getDisplayContainer = getDisplayContainer;
                        function getScrollContainer($grid) {
                            return $("#" + $grid.attr("id") + "_scrollContainer");
                        }
                        utils.getScrollContainer = getScrollContainer;
                        function startEdit($grid, cell) {
                            var visibleColumns = getVisibleColumns($grid);
                            for (var i = 0; i < visibleColumns.length; i++) {
                                if (visibleColumns[i].key === cell.columnKey) {
                                    $grid.igGridUpdating("startEdit", cell.id, i);
                                    break;
                                }
                            }
                        }
                        utils.startEdit = startEdit;
                        function hideColumns($grid, columns) {
                            $grid.igGridHiding("hideMultiColumns", columns);
                        }
                        utils.hideColumns = hideColumns;
                        function showColumns($grid, columns) {
                            $grid.igGridHiding("showMultiColumns", columns);
                        }
                        utils.showColumns = showColumns;
                        function disableNtsControl($grid, cell, controlType) {
                            var control = ntsControls.getControl(controlType);
                            if (uk.util.isNullOrUndefined(control))
                                return;
                            control.disable(cell.$element);
                            if (!cell.$element.hasClass(color.Disable))
                                cell.$element.addClass(color.Disable);
                            color.pushDisable($grid, { id: cell.id, columnKey: cell.columnKey });
                        }
                        utils.disableNtsControl = disableNtsControl;
                        function analyzeColumns(columns) {
                            var flatCols = [];
                            flatColumns(columns, flatCols);
                            return flatCols;
                        }
                        utils.analyzeColumns = analyzeColumns;
                        function flatColumns(columns, flatCols) {
                            _.forEach(columns, function (column) {
                                if (uk.util.isNullOrUndefined(column.group)) {
                                    flatCols.push(column);
                                    return;
                                }
                                flatColumns(column.group, flatCols);
                            });
                        }
                        function setChildrenTabIndex($grid, index) {
                            var container = $grid.igGrid("container");
                            $(container).attr("tabindex", 0);
                            $(container).find("tr, th, td").attr("tabindex", index);
                        }
                        utils.setChildrenTabIndex = setChildrenTabIndex;
                        function outsideGrid($grid, target) {
                            return !$grid.is(target) && $grid.has(target).length === 0;
                        }
                        utils.outsideGrid = outsideGrid;
                    })(utils || (utils = {}));
                })(ntsGrid = jqueryExtentions.ntsGrid || (jqueryExtentions.ntsGrid = {}));
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ntsgrid-jquery-ext.js.map