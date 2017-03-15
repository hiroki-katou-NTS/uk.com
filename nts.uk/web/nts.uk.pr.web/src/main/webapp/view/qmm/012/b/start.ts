module qmm012.b {
    __viewContext.ready(function() {
        let screenModel = new qmm012.b.ScreenModel(
        new c.viewmodel.ScreenModel(), 
        new d.viewmodel.ScreenModel(), 
        new e.viewmodel.ScreenModel(), 
        new f.viewmodel.ScreenModel()
        );
        screenModel.screenModelB = new viewmodel.ScreenModel(screenModel);
        __viewContext.bind(screenModel);

    });

    export class ScreenModel {
        screenModelB: b.viewmodel.ScreenModel;
        screenModelC: c.viewmodel.ScreenModel;
        screenModelD: d.viewmodel.ScreenModel;
        screenModelE: e.viewmodel.ScreenModel;
        screenModelF: f.viewmodel.ScreenModel;

        constructor(
            screenModelC: c.viewmodel.ScreenModel,
            screenModelD: d.viewmodel.ScreenModel,
            screenModelE: e.viewmodel.ScreenModel,
            screenModelF: f.viewmodel.ScreenModel
        ) {
            this.screenModelC = screenModelC;
            this.screenModelD = screenModelD;
            this.screenModelE = screenModelE;
            this.screenModelF = screenModelF;
        }

    }
}
