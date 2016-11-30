module nts.uk.pr.view.qpp005 {
    __viewContext.ready(function() {

        var screenModel = new nts.uk.pr.view.qpp005.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            var categoryPayment: nts.uk.pr.view.qpp005.viewmodel.LayoutMasterCategoryViewModel = (<any>screenModel).paymentDataResult().categories()[0];
            var categoryDeduct: nts.uk.pr.view.qpp005.viewmodel.LayoutMasterCategoryViewModel = (<any>screenModel).paymentDataResult().categories()[1];
            var categoryArticle: nts.uk.pr.view.qpp005.viewmodel.LayoutMasterCategoryViewModel = (<any>screenModel).paymentDataResult().categories()[3];

            //        var $paymentLastItem = 'ct' + (<any>categoryPayment).categoryAttribute() + '_' + ((<any>categoryPayment).lineCounts() - 1) + '_8';
            //        $("#" + $paymentLastItem).addClass('disabled');
            //        var $deductLastItem = (<any>categoryDeduct).categoryAttribute() + '_' + ((<any>categoryDeduct).lineCounts() - 1) + '_8';
            //        $("#" + $deductLastItem).addClass('disabled');
            //        var $articleLastItem = (<any>categoryArticle).categoryAttribute() + '_' + ((<any>categoryArticle).lineCounts() - 1) + '_8';
            //        $("#" + $articleLastItem).addClass('disabled');

            $('#pơpup-orientation').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('#btSetting')
                }
            });

            utils.gridSetup(screenModel.switchButton().selectedRuleCode());
        });
    });

    export module utils {
        export function gridSetup(orientation) {
            $('.tb-category').each(function() {

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

                        if (input === lastItemId) $input.addClass('disabled');
                        if ($input.length === 1 && input !== lastItemId) vlist.push($input);
                    }
                }
                for (var i = 0; i < vlist.length; i++) {
                    var next = (i === vlist.length - 1) ? vlist[0] : vlist[i + 1];
                    vlist[i].data('vnext', next);
                }

                window.hlist = [];
                $tbl.find('input').not('.disabled').each(function() { hlist.push($(this)); });
                for (var i = 0; i < hlist.length; i++) {
                    var next = (i === hlist.length - 1) ? hlist[0] : hlist[i + 1];
                    hlist[i].data('hnext', next);
                }

                $tbl.on('keydown', 'input', function(e) {
                    if (e.keyCode == 9) {
                        $(this).data(orientation).focus();
                        return false;
                    }
                })
            });
        }
    }
}