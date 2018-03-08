module nts.uk.com.view.cmf002.o.viewmodel {
    import model = cmf002.share.model;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog.info;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);

        listCondition: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedConditionCd: KnockoutObservable<string> = ko.observable('');
        selectedConditionName: KnockoutObservable<string> = ko.observable('');

        periodDateValue: KnockoutObservable<any> = ko.observable({});

        listOutputItem: KnockoutObservableArray<model.StandardOutputItem> = ko.observableArray([]);
        selectedOutputItemCode: KnockoutObservable<string> = ko.observable('');

        listOutputCondition: KnockoutObservableArray<OutputCondition> = ko.observableArray([]);
        selectedOutputConditionItem: KnockoutObservable<string> = ko.observable('');

        constructor() {
            var self = this;

            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' },
                { content: '.step-4' }
            ];
            self.stepSelected = ko.observable({ id: 'step-4', content: '.step-4' });

            self.loadListCondition();

            self.selectedConditionCd.subscribe(function(data: any) {
                if (data) {
                    let item = _.find(ko.toJS(self.listCondition), (x: model.ItemModel) => x.code == data);
                    self.selectedConditionName(item.name);
                }
                else {
                    self.selectedConditionName('');
                }
            });

            self.loadListOutputItem();
            self.loadListOutputCondition()
        }

        selectStandardMode() {
            $('#ex_output_wizard').ntsWizard("next");
        }

        next() {
            let self = this;
            console.log(self.periodDateValue())
            $('#ex_output_wizard').ntsWizard("next");
        }
        previous() {
            $('#ex_output_wizard').ntsWizard("prev");
        }

        loadListCondition() {
            let self = this;

            self.listCondition([
                new model.ItemModel(111, 'test a'),
                new model.ItemModel(222, 'test b'),
                new model.ItemModel(333, 'test c'),
                new model.ItemModel(444, 'test d')
            ]);
            self.selectedConditionCd('1');
            self.selectedConditionName('test a');
        }

        loadListOutputItem() {
            let self = this;

            for (let i = 0; i < 20; i++) {
                self.listOutputItem.push(new model.StandardOutputItem('00' + i, 'Test ' + i, '', '', 0));
            }
        }

        loadListOutputCondition() {
            let self = this;

            for (let i = 0; i < 10; i++) {
                self.listOutputCondition.push(new OutputCondition('item ' + i, 'Condition ' + i));
            }
        }
    }

    class OutputCondition {
        itemName: string;
        condition: string;
        constructor(itemName: string, condition: string) {
            this.itemName = itemName;
            this.condition = condition;
        }
    }
}