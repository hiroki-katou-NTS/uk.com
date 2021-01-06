module nts.uk.at.view.kaf007_ref.c.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import ModelDto = nts.uk.at.view.kaf007_ref.shr.viewmodel.ModelDto;
    import ReflectWorkChangeApp = nts.uk.at.view.kaf007_ref.shr.viewmodel.ReflectWorkChangeApp;

    @component({
        name: 'kaf011-b',
        template: '/nts.uk.at.web/view/kaf/011/b/index.xhtml'
    })
    class Kaf011BViewModel extends ko.ViewModel {

        appType: KnockoutObservable<number> = ko.observable(AppType.WORK_CHANGE_APPLICATION);
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        appWorkChange: AppWorkChange;
        approvalReason: KnockoutObservable<string>;
        model: KnockoutObservable<ModelDto> = ko.observable(null);
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
        reflectWorkChange: ReflectWorkChangeApp;
        comment1: KnockoutObservable<string> = ko.observable("");
        comment2: KnockoutObservable<string> = ko.observable("");
        isStraightGo: KnockoutObservable<boolean> = ko.observable(false);
        isStraightBack: KnockoutObservable<boolean> = ko.observable(false);
        setupType: number;
        workTypeLst: any[];
        isEdit: KnockoutObservable<boolean> = ko.observable(true);

        created(
            params: {
                appType: any,
                application: any,
                printContentOfEachAppDto: PrintContentOfEachAppDto,
                approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void) => void,
                eventReload: (evt: () => void) => void
            }
        ) 
		{
            const vm = this;
      
        }

        mounted() {
            const vm = this;
        }

    }

}