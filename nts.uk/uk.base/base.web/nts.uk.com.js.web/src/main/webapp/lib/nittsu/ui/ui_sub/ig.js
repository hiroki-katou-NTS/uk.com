var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var ig;
            (function (ig) {
                var grid;
                (function (grid) {
                    function getScrollContainer($grid) {
                        var $scroll = $grid.igGrid("scrollContainer");
                        if ($scroll.length === 1)
                            return $scroll;
                        return $("#" + $grid.attr("id") + "_scrollContainer");
                    }
                    grid.getScrollContainer = getScrollContainer;
                    function getRowIdFrom($anyElementInRow) {
                        return $anyElementInRow.closest('tr').attr('data-id');
                    }
                    grid.getRowIdFrom = getRowIdFrom;
                    function getRowIndexFrom($anyElementInRow) {
                        return parseInt($anyElementInRow.closest('tr').attr('data-row-idx'), 10);
                    }
                    grid.getRowIndexFrom = getRowIndexFrom;
                    function expose(targetRow, $grid) {
                        var $scroll = getScrollContainer($grid);
                        $scroll.exposeVertically(targetRow.element);
                    }
                    grid.expose = expose;
                    var virtual;
                    (function (virtual) {
                        function getDisplayContainer(gridId) {
                            return $('#' + gridId + '_displayContainer');
                        }
                        virtual.getDisplayContainer = getDisplayContainer;
                        function getVisibleRows(gridId) {
                            return $('#' + gridId + ' > tbody > tr:visible');
                        }
                        virtual.getVisibleRows = getVisibleRows;
                        function getFirstVisibleRow(gridId) {
                            var top = getDisplayContainer(gridId).scrollTop();
                            var visibleRows = getVisibleRows(gridId);
                            for (var i = 0; i < visibleRows.length; i++) {
                                var $row = $(visibleRows[i]);
                                if (visibleRows[i].offsetTop + $row.height() > top) {
                                    return $row;
                                }
                            }
                        }
                        virtual.getFirstVisibleRow = getFirstVisibleRow;
                        function getLastVisibleRow(gridId) {
                            var $displayContainer = getDisplayContainer(gridId);
                            var bottom = $displayContainer.scrollTop() + $displayContainer.height();
                            return getVisibleRows(gridId).filter(function () {
                                return this.offsetTop < bottom;
                            }).last();
                        }
                        virtual.getLastVisibleRow = getLastVisibleRow;
                        function expose(targetRow, $grid) {
                            if (targetRow.index === undefined) {
                                $grid.igGrid("virtualScrollTo", dataSource.getIndexOfKey(targetRow.id, $grid) + 1);
                                return;
                            }
                            var rowHeight = targetRow.element.outerHeight();
                            var targetTop = targetRow.index * rowHeight;
                            var targetBottom = targetTop + rowHeight;
                            var $scroll = getScrollContainer($grid);
                            var viewHeight = $scroll.height();
                            var viewTop = $scroll.scrollTop();
                            var viewBottom = viewTop + viewHeight;
                            if (viewTop <= targetTop && targetBottom <= viewBottom) {
                                return;
                            }
                            $grid.igGrid("virtualScrollTo", targetRow.index + 1);
                        }
                        virtual.expose = expose;
                    })(virtual = grid.virtual || (grid.virtual = {}));
                    var dataSource;
                    (function (dataSource) {
                        function getIndexOfKey(targetKey, $grid) {
                            var option = $grid.igGrid("option");
                            return _.findIndex(option.dataSource, function (s) { return s[option.primaryKey].toString() === targetKey.toString(); });
                        }
                        dataSource.getIndexOfKey = getIndexOfKey;
                    })(dataSource = grid.dataSource || (grid.dataSource = {}));
                    var header;
                    (function (header) {
                        function getCell(gridId, columnKey) {
                            var $headers = $('#' + gridId).igGrid("headersTable");
                            return $headers.find('#' + gridId + '_' + columnKey);
                        }
                        header.getCell = getCell;
                        function getLabel(gridId, columnKey) {
                            return getCell(gridId, columnKey).find('span');
                        }
                        header.getLabel = getLabel;
                    })(header = grid.header || (grid.header = {}));
                })(grid = ig.grid || (ig.grid = {}));
                var tree;
                (function (tree) {
                    var grid;
                    (function (grid) {
                        function expandTo(targetKey, $treeGrid) {
                            var option = $treeGrid.igTreeGrid("option");
                            var ancestorKeys = dataSource.collectAncestorKeys(targetKey, option.dataSource, option.primaryKey, option.childDataKey);
                            if (ancestorKeys === null) {
                                return;
                            }
                            var expand = function (currentIndex) {
                                if (currentIndex >= ancestorKeys.length)
                                    return;
                                $treeGrid.igTreeGrid("expandRow", ancestorKeys[currentIndex]);
                                setTimeout(function () { expand(currentIndex + 1); }, 0);
                            };
                            expand(0);
                            setTimeout(function () {
                                scrollTo(targetKey, $treeGrid);
                            }, 1);
                        }
                        grid.expandTo = expandTo;
                        function scrollTo(targetKey, $treeGrid) {
                            var $scroll = $treeGrid.igTreeGrid("scrollContainer");
                            var $targetNode = $treeGrid.find("tr[data-id='" + targetKey + "']").first();
                            if ($targetNode.length === 0)
                                return;
                            $scroll.exposeVertically($targetNode);
                        }
                        grid.scrollTo = scrollTo;
                    })(grid = tree.grid || (tree.grid = {}));
                    var dataSource;
                    (function (dataSource_1) {
                        function collectAncestorKeys(targetKey, dataSource, primaryKey, childDataKey) {
                            if (typeof dataSource === "undefined") {
                                return null;
                            }
                            for (var i = 0, len = dataSource.length; i < len; i++) {
                                var currentData = dataSource[i];
                                if (currentData[primaryKey] === targetKey) {
                                    return [targetKey];
                                }
                                var children = currentData[childDataKey];
                                var results = collectAncestorKeys(targetKey, children, primaryKey, childDataKey);
                                if (results !== null) {
                                    results.unshift(currentData[primaryKey]);
                                    return results;
                                }
                            }
                            return null;
                        }
                        dataSource_1.collectAncestorKeys = collectAncestorKeys;
                    })(dataSource = tree.dataSource || (tree.dataSource = {}));
                })(tree = ig.tree || (ig.tree = {}));
            })(ig = ui.ig || (ui.ig = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ig.js.map