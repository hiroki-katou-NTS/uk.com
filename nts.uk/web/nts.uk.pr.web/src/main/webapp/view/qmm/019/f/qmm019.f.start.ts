module qmm019.f {
    __viewContext.ready(function() {
        let param = nts.uk.ui.windows.getShared('param');
        let screenModel = new qmm019.f.ScreenModel(
            new f.salaryItem.viewmodel.ScreenModel(),
            new f.deductItem.viewmodel.ScreenModel(),
            new f.attendItem.viewmodel.ScreenModel(),
            new f.articleItem.viewmodel.ScreenModel()
        );
        var screenModelMain = new qmm019.f.viewmodel.ScreenModel(screenModel, param);
        screenModelMain.start().done(function() {
            __viewContext.bind(screenModelMain);
        });
    });

    export class ScreenModel {
        salaryItemScreen: f.salaryItem.viewmodel.ScreenModel;
        deductItemScreen: f.deductItem.viewmodel.ScreenModel;
        attendItemScreen: f.attendItem.viewmodel.ScreenModel;
        articleItemScreen: f.articleItem.viewmodel.ScreenModel;
        constructor(
            salaryItemScreen: f.salaryItem.viewmodel.ScreenModel,
            deductItemScreen: f.deductItem.viewmodel.ScreenModel,
            attendItemScreen: f.attendItem.viewmodel.ScreenModel,
            articleItemScreen: f.articleItem.viewmodel.ScreenModel
        ) {
            this.salaryItemScreen = salaryItemScreen;
            this.deductItemScreen = deductItemScreen;
            this.attendItemScreen = attendItemScreen;
            this.articleItemScreen = articleItemScreen;
        }

    }
} 1