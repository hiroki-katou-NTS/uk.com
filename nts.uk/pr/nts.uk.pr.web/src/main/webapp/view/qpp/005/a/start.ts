__viewContext.ready(function() {
    var screenModel = new nts.uk.pr.view.qpp005.viewmodel.ScreenModel();
    screenModel.startPage().done(function() {
        __viewContext.bind(screenModel);

        var categoryPayment: nts.uk.pr.view.qpp005.viewmodel.LayoutMasterCategoryViewModel = (<any>screenModel).paymentDataResult().categories()[0];
        var categoryDeduct: nts.uk.pr.view.qpp005.viewmodel.LayoutMasterCategoryViewModel = (<any>screenModel).paymentDataResult().categories()[1];
        var categoryArticle: nts.uk.pr.view.qpp005.viewmodel.LayoutMasterCategoryViewModel = (<any>screenModel).paymentDataResult().categories()[3];

        var $paymentLastItem = categoryPayment.categoryAttribute() + '_' + (categoryPayment.lineCounts() - 1) + '_8';
        $("#" + $paymentLastItem).addClass('disabled');
        var $deductLastItem = categoryDeduct.categoryAttribute() + '_' + (categoryDeduct.lineCounts() - 1) + '_8';
        $("#" + $deductLastItem).addClass('disabled');
        var $articleLastItem = categoryArticle.categoryAttribute() + '_' + (categoryArticle.lineCounts() - 1) + '_8';
        $("#" + $articleLastItem).addClass('disabled');
    });
});
