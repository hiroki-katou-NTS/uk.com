module nts.uk.at.view.kaf022.j.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelJ {
        itemListD15: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_391')},
            {code: 1, name: text('KAF022_392')}
        ]);
        itemListD13: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_389')},
            {code: 0, name: text('KAF022_390')}
        ]);
        supportFrames: KnockoutObservableArray<ItemModel>;

        cancelAtr: KnockoutObservable<number> = ko.observable(0);

        workComment1Content: KnockoutObservable<string> = ko.observable("");
        workComment1Color: KnockoutObservable<string> = ko.observable("");
        workComment1Bold: KnockoutObservable<boolean> = ko.observable(false);
        workComment2Content: KnockoutObservable<string> = ko.observable("");
        workComment2Color: KnockoutObservable<string> = ko.observable("");
        workComment2Bold: KnockoutObservable<boolean> = ko.observable(false);

        goOutPrivateDispAtr: KnockoutObservable<number> = ko.observable(0);
        goOutOfficeDispAtr: KnockoutObservable<number> = ko.observable(0);
        goOutCompensationDispAtr: KnockoutObservable<number> = ko.observable(0);
        goOutUnionDispAtr: KnockoutObservable<number> = ko.observable(0);
        goOutComment1Content: KnockoutObservable<string> = ko.observable("");
        goOutComment1Color: KnockoutObservable<string> = ko.observable("");
        goOutComment1Bold: KnockoutObservable<boolean> = ko.observable(false);
        goOutComment2Content: KnockoutObservable<string> = ko.observable("");
        goOutComment2Color: KnockoutObservable<string> = ko.observable("");
        goOutComment2Bold: KnockoutObservable<boolean> = ko.observable(false);

        childCareComment1Content: KnockoutObservable<string> = ko.observable("");
        childCareComment1Color: KnockoutObservable<string> = ko.observable("");
        childCareComment1Bold: KnockoutObservable<boolean> = ko.observable(false);
        childCareComment2Content: KnockoutObservable<string> = ko.observable("");
        childCareComment2Color: KnockoutObservable<string> = ko.observable("");
        childCareComment2Bold: KnockoutObservable<boolean> = ko.observable(false);

        supportFrameDispNO: KnockoutObservable<number> = ko.observable(0);
        supportComment1Content: KnockoutObservable<string> = ko.observable("");
        supportComment1Color: KnockoutObservable<string> = ko.observable("");
        supportComment1Bold: KnockoutObservable<boolean> = ko.observable(false);
        supportComment2Content: KnockoutObservable<string> = ko.observable("");
        supportComment2Color: KnockoutObservable<string> = ko.observable("");
        supportComment2Bold: KnockoutObservable<boolean> = ko.observable(false);

        careComment1Content: KnockoutObservable<string> = ko.observable("");
        careComment1Color: KnockoutObservable<string> = ko.observable("");
        careComment1Bold: KnockoutObservable<boolean> = ko.observable(false);
        careComment2Content: KnockoutObservable<string> = ko.observable("");
        careComment2Color: KnockoutObservable<string> = ko.observable("");
        careComment2Bold: KnockoutObservable<boolean> = ko.observable(false);

        breakComment1Content: KnockoutObservable<string> = ko.observable("");
        breakComment1Color: KnockoutObservable<string> = ko.observable("");
        breakComment1Bold: KnockoutObservable<boolean> = ko.observable(false);
        breakComment2Content: KnockoutObservable<string> = ko.observable("");
        breakComment2Color: KnockoutObservable<string> = ko.observable("");
        breakComment2Bold: KnockoutObservable<boolean> = ko.observable(false);

        imageRecordComment1Content: KnockoutObservable<string> = ko.observable("");
        imageRecordComment1Color: KnockoutObservable<string> = ko.observable("");
        imageRecordComment1Bold: KnockoutObservable<boolean> = ko.observable(false);
        imageRecordComment2Content: KnockoutObservable<string> = ko.observable("");
        imageRecordComment2Color: KnockoutObservable<string> = ko.observable("");
        imageRecordComment2Bold: KnockoutObservable<boolean> = ko.observable(false);

        workTimeReflectAtr: KnockoutObservable<number> = ko.observable(0);
        extraWorkTimeReflectAtr: KnockoutObservable<number> = ko.observable(0);
        goOutTimeReflectAtr: KnockoutObservable<number> = ko.observable(0);
        childCareTimeReflecAtr: KnockoutObservable<number> = ko.observable(0);
        supportTimeReflecAtr: KnockoutObservable<number> = ko.observable(0);
        careTimeReflectAtr: KnockoutObservable<number> = ko.observable(0);
        breakTimeReflectAtr: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            const self = this;

            self.supportFrames = ko.observableArray([]);
            for (let i = 1; i <= 20; i++) {
                self.supportFrames.push(new ItemModel(i, i.toString()));
            }

            $("#fixed-table-j1").ntsFixedTable({});
            $("#fixed-table-j2").ntsFixedTable({});
            $("#fixed-table-j3").ntsFixedTable({});
            $("#fixed-table-j4").ntsFixedTable({});
            $("#fixed-table-j5").ntsFixedTable({});
            $("#fixed-table-j6").ntsFixedTable({});
            $("#fixed-table-j7").ntsFixedTable({});
            $("#fixed-table-j8").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            const dataSetting = allData.appStampSetting;
            const dataReflect = allData.appStampReflect;
            if (dataSetting) {
                self.cancelAtr(dataSetting.useCancelFunction || 0);
                self.supportFrameDispNO(dataSetting.supportFrameDispNO);
                const goOutTypeDispControl = dataSetting.goOutTypeDispControl || [];
                goOutTypeDispControl.forEach(d => {
                    switch (d.goOutType) {
                        case 0:
                            self.goOutPrivateDispAtr(d.display || 0);
                            break;
                        case 1:
                            self.goOutOfficeDispAtr(d.display || 0);
                            break;
                        case 2:
                            self.goOutCompensationDispAtr(d.display || 0);
                            break;
                        case 3:
                            self.goOutUnionDispAtr(d.display || 0);
                            break;
                        default:
                            break;
                    }
                });
                const settingForEachTypeLst = dataSetting.settingForEachTypeLst || [];
                settingForEachTypeLst.forEach(t => {
                    switch (t.stampAtr) {
                        case 0:
                            self.workComment1Content(t.topComment.comment);
                            self.workComment1Color(t.topComment.colorCode);
                            self.workComment1Bold(t.topComment.bold);
                            self.workComment2Content(t.bottomComment.comment);
                            self.workComment2Color(t.bottomComment.colorCode);
                            self.workComment2Bold(t.bottomComment.bold);
                            break;
                        case 1:
                            self.goOutComment1Content(t.topComment.comment);
                            self.goOutComment1Color(t.topComment.colorCode);
                            self.goOutComment1Bold(t.topComment.bold);
                            self.goOutComment2Content(t.bottomComment.comment);
                            self.goOutComment2Color(t.bottomComment.colorCode);
                            self.goOutComment2Bold(t.bottomComment.bold);
                            break;
                        case 2:
                            self.childCareComment1Content(t.topComment.comment);
                            self.childCareComment1Color(t.topComment.colorCode);
                            self.childCareComment1Bold(t.topComment.bold);
                            self.childCareComment2Content(t.bottomComment.comment);
                            self.childCareComment2Color(t.bottomComment.colorCode);
                            self.childCareComment2Bold(t.bottomComment.bold);
                            break;
                        case 3:
                            self.supportComment1Content(t.topComment.comment);
                            self.supportComment1Color(t.topComment.colorCode);
                            self.supportComment1Bold(t.topComment.bold);
                            self.supportComment2Content(t.bottomComment.comment);
                            self.supportComment2Color(t.bottomComment.colorCode);
                            self.supportComment2Bold(t.bottomComment.bold);
                            break;
                        case 4:
                            self.careComment1Content(t.topComment.comment);
                            self.careComment1Color(t.topComment.colorCode);
                            self.careComment1Bold(t.topComment.bold);
                            self.careComment2Content(t.bottomComment.comment);
                            self.careComment2Color(t.bottomComment.colorCode);
                            self.careComment2Bold(t.bottomComment.bold);
                            break;
                        case 5:
                            self.breakComment1Content(t.topComment.comment);
                            self.breakComment1Color(t.topComment.colorCode);
                            self.breakComment1Bold(t.topComment.bold);
                            self.breakComment2Content(t.bottomComment.comment);
                            self.breakComment2Color(t.bottomComment.colorCode);
                            self.breakComment2Bold(t.bottomComment.bold);
                            break;
                        case 6:
                            self.imageRecordComment1Content(t.topComment.comment);
                            self.imageRecordComment1Color(t.topComment.colorCode);
                            self.imageRecordComment1Bold(t.topComment.bold);
                            self.imageRecordComment2Content(t.bottomComment.comment);
                            self.imageRecordComment2Color(t.bottomComment.colorCode);
                            self.imageRecordComment2Bold(t.bottomComment.bold);
                            break;
                        default:
                            break;
                    }
                })
            }
            if (dataReflect) {
                self.workTimeReflectAtr(dataReflect.workTimeReflectAtr || 0);
                self.extraWorkTimeReflectAtr(dataReflect.extraWorkTimeReflectAtr || 0);
                self.goOutTimeReflectAtr(dataReflect.goOutTimeReflectAtr || 0);
                self.childCareTimeReflecAtr(dataReflect.childCareTimeReflecAtr || 0);
                self.supportTimeReflecAtr(dataReflect.supportTimeReflecAtr || 0);
                self.careTimeReflectAtr(dataReflect.careTimeReflectAtr || 0);
                self.breakTimeReflectAtr(dataReflect.breakTimeReflectAtr || 0);
            }
        }

        collectData(): any {
            const self = this;
            return {
                appStampSetting: {
                    cancelAtr: self.cancelAtr(),

                    workComment1Content: self.workComment1Content(),
                    workComment1Color: self.workComment1Color(),
                    workComment1Bold: self.workComment1Bold(),
                    workComment2Content: self.workComment2Content(),
                    workComment2Color: self.workComment2Color(),
                    workComment2Bold: self.workComment2Bold(),

                    goOutPrivateDispAtr: self.goOutPrivateDispAtr(),
                    goOutOfficeDispAtr: self.goOutOfficeDispAtr(),
                    goOutCompensationDispAtr: self.goOutCompensationDispAtr(),
                    goOutUnionDispAtr: self.goOutUnionDispAtr(),
                    goOutComment1Content: self.goOutComment1Content(),
                    goOutComment1Color: self.goOutComment1Color(),
                    goOutComment1Bold: self.goOutComment1Bold(),
                    goOutComment2Content: self.goOutComment2Content(),
                    goOutComment2Color: self.goOutComment2Color(),
                    goOutComment2Bold: self.goOutComment2Bold(),

                    childCareComment1Content: self.childCareComment1Content(),
                    childCareComment1Color: self.childCareComment1Color(),
                    childCareComment1Bold: self.childCareComment1Bold(),
                    childCareComment2Content: self.childCareComment2Content(),
                    childCareComment2Color: self.childCareComment2Color(),
                    childCareComment2Bold: self.childCareComment2Bold(),

                    supportFrameDispNO: self.supportFrameDispNO(),
                    supportComment1Content: self.supportComment1Content(),
                    supportComment1Color: self.supportComment1Color(),
                    supportComment1Bold: self.supportComment1Bold(),
                    supportComment2Content: self.supportComment2Content(),
                    supportComment2Color: self.supportComment2Color(),
                    supportComment2Bold: self.supportComment2Bold(),

                    careComment1Content: self.careComment1Content(),
                    careComment1Color: self.careComment1Color(),
                    careComment1Bold: self.careComment1Bold(),
                    careComment2Content: self.careComment2Content(),
                    careComment2Color: self.careComment2Color(),
                    careComment2Bold: self.careComment2Bold(),

                    breakComment1Content: self.breakComment1Content(),
                    breakComment1Color: self.breakComment1Color(),
                    breakComment1Bold: self.breakComment1Bold(),
                    breakComment2Content: self.breakComment2Content(),
                    breakComment2Color: self.breakComment2Color(),
                    breakComment2Bold: self.breakComment2Bold(),

                    imageRecordComment1Content: self.imageRecordComment1Content(),
                    imageRecordComment1Color: self.imageRecordComment1Color(),
                    imageRecordComment1Bold: self.imageRecordComment1Bold(),
                    imageRecordComment2Content: self.imageRecordComment2Content(),
                    imageRecordComment2Color: self.imageRecordComment2Color(),
                    imageRecordComment2Bold: self.imageRecordComment2Bold()
                },
                appStampReflect: {
                    workTimeReflectAtr: self.workTimeReflectAtr(),
                    extraWorkTimeReflectAtr: self.extraWorkTimeReflectAtr(),
                    goOutTimeReflectAtr: self.goOutTimeReflectAtr(),
                    childCareTimeReflecAtr: self.childCareTimeReflecAtr(),
                    supportTimeReflecAtr: self.supportTimeReflecAtr(),
                    careTimeReflectAtr: self.careTimeReflectAtr(),
                    breakTimeReflectAtr: self.breakTimeReflectAtr()
                }
            };
        }

    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}