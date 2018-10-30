module nts.uk.pr.view.qmm019.h.viewmodel {
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {

        itemList: KnockoutObservableArray<shareModel.BoxModel>;
        selectedId: KnockoutObservable<number>;

        existingSpecs: KnockoutObservableArray<ExistingSpec>;
        existingSpecCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        specInfo: KnockoutObservable<string>;
        specCode: KnockoutObservable<string>;
        specName: KnockoutObservable<string>;
        startDate: KnockoutObservable<number>;
        startDateJp: KnockoutObservable<string>;

        constructor() {
            let self = this;

            self.itemList = ko.observableArray(shareModel.getSpecCreateAtr());
            self.selectedId = ko.observable(shareModel.SpecCreateAtr.NEW);

            self.existingSpecs = ko.observableArray([
                new ExistingSpec('1', '基本給', '123345'),
                new ExistingSpec('2', '役職手当', '456778'),
                new ExistingSpec('3', '基本給ながい文字列ながい文字列ながい文字列', 'fghgfhret')
            ]);
            self.existingSpecCode = ko.observable(null);


            self.isEnable = ko.observable(false);
            self.specInfo = ko.observable("");
            self.specCode = ko.observable(null);
            self.specName = ko.observable(null);
            self.startDate = ko.observable(null);
            self.startDateJp = ko.observable(null);

            self.selectedId.subscribe(value => {
                self.isEnable(value == shareModel.SpecCreateAtr.COPY);
            })
            self.existingSpecCode.subscribe(value => {
                let spec: ExistingSpec = _.find(self.existingSpecs(), (item: ExistingSpec) => {
                    return item.code == value;
                })
                self.specInfo(spec.histId);
            })
            self.startDate.subscribe(value => {
                let dateJp = nts.uk.time.yearmonthInJapanEmpire(value);
                if(dateJp == null){
                    self.startDateJp(null);
                }else{
                    self.startDateJp(dateJp.toString());
                }
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        decide() {
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }

    class ExistingSpec {
        code: string;
        name: string;
        histId: string;

        constructor(code: string, name: string, histId: string) {
            this.code = code;
            this.name = name;
            this.histId = histId;
        }
    }
}