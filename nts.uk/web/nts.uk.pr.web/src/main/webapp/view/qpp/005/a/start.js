var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp005;
                (function (qpp005) {
                    var a;
                    (function (a) {
                        __viewContext.ready(function () {
                            var screenModel = new nts.uk.pr.view.qpp005.a.viewmodel.ScreenModel();
                            screenModel.startPage().done(function () {
                                __viewContext.bind(screenModel);
                                $('#pơpup-orientation').ntsPopup({
                                    position: {
                                        my: 'left top',
                                        at: 'left bottom',
                                        of: $('#btSetting')
                                    }
                                });
                                utils.gridSetup(screenModel.switchButton().selectedRuleCode());
                            }).fail(function () {
                                __viewContext.bind(screenModel);
                            });
                        });
                        var utils;
                        (function (utils) {
                            function gridSetup(orientation) {
                                window.bakData = [];
                                window.lastItems = [];
                                $('.tb-category').each(function () {
                                    var tblid = this.id;
                                    var $tbl = $("#" + tblid);
                                    window.orientation = orientation;
                                    window.vlist = [];
                                    var countColumns = 9;
                                    var countRows = $tbl.find('tr').length;
                                    var lastItemId = tblid + '_' + (countRows - 1) + '_' + 8;
                                    for (var c = 0; c < countColumns; c++) {
                                        for (var r = 0; r < countRows; r++) {
                                            var input = tblid + '_' + r + '_' + c;
                                            var $input = $('#' + input);
                                            if (input === lastItemId) {
                                                $input.addClass('disabled');
                                                $input.addClass('item-grey');
                                                lastItems.push(new itemBAK(input, $input.val(), $input.css('backgroundColor')));
                                            }
                                            if ($input.length === 1 && input !== lastItemId) {
                                                vlist.push($input);
                                                bakData.push(new itemBAK(input, $input.val(), $input.css('backgroundColor')));
                                            }
                                        }
                                    }
                                    for (var i = 0; i < vlist.length; i++) {
                                        var next = (i === vlist.length - 1) ? vlist[0] : vlist[i + 1];
                                        vlist[i].data('vnext', next);
                                    }
                                    window.hlist = [];
                                    $tbl.find('input').not('.disabled').each(function () {
                                        hlist.push($(this));
                                    });
                                    for (var i = 0; i < hlist.length; i++) {
                                        var next = (i === hlist.length - 1) ? hlist[0] : hlist[i + 1];
                                        hlist[i].data('hnext', next);
                                    }
                                    $tbl.on('keydown', 'input', function (e) {
                                        if (e.keyCode == 9) {
                                            $(this).data(orientation).focus();
                                            return false;
                                        }
                                    });
                                });
                            }
                            utils.gridSetup = gridSetup;
                            function setBackgroundColorForItem(nId, nVal, sumScopeAtr) {
                                var include = ko.utils.arrayFirst(window.bakData, function (item) {
                                    return item.id === nId && item.value === nVal;
                                });
                                var isCorrectFlag = false;
                                if (include) {
                                    $("#" + nId).css('background', include.color);
                                    $("#" + nId).find('input').css('background', include.color);
                                }
                                else {
                                    $("#" + nId).css('background', '#bdd7ee');
                                    $("#" + nId).find('input').css('background', '#bdd7ee');
                                    isCorrectFlag = true;
                                }
                                var ctId = nId.split('_')[0];
                                if (sumScopeAtr === 1) {
                                    if (ctId === 'ct0') {
                                        $("#" + window.lastItems[0].id).css('background', '#f4b084');
                                        $("#" + window.lastItems[window.lastItems.length - 1].id).css('background', '#f4b084');
                                    }
                                    if (ctId === 'ct1') {
                                        $("#" + window.lastItems[1].id).css('background', '#f4b084');
                                        $("#" + window.lastItems[window.lastItems.length - 1].id).css('background', '#f4b084');
                                    }
                                }
                                return isCorrectFlag;
                            }
                            utils.setBackgroundColorForItem = setBackgroundColorForItem;
                            var itemBAK = (function () {
                                function itemBAK(id, value, color) {
                                    var self = this;
                                    self.id = id;
                                    self.value = value;
                                    self.color = color;
                                }
                                return itemBAK;
                            }());
                            utils.itemBAK = itemBAK;
                        })(utils = a.utils || (a.utils = {}));
                    })(a = qpp005.a || (qpp005.a = {}));
                })(qpp005 = view.qpp005 || (view.qpp005 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
