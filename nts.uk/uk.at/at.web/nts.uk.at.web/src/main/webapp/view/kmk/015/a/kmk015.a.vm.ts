module nts.uk.at.view.kmk015.a {
    import OptionalItemHeaderDto = nts.uk.at.view.kmk015.a.service.model.WorkType;

    export module viewmodel {

        export class ScreenModel {
            columns: any;
            selectedCode: KnockoutObservable<string>;
            listWorkType: KnockoutObservableArray<OptionalItemHeaderDto>;
            hasSelected: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.listWorkType = ko.observableArray([]);
                self.hasSelected = ko.observable(true);

                self.selectedCode = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK015_3'), key: 'workTypeCode', width: 40 },
                    { headerText: nts.uk.resource.getText('KMK015_4'), key: 'name', width: 180 },
                    {
                        headerText: nts.uk.resource.getText('KMK015_5'), key: 'abolishAtr', width: 50,
                        formatter: used => {
                            if (used == 1) {
                                return '<div style="text-align: center;max-height: 18px;">'
                                    + '<i class="icon icon-78"></i></div>';
                            }
                            return '';
                        }
                    }
                ]);

            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui
                nts.uk.ui.block.invisible();

                // get list worktype
                service.findListWorkType().done(res => {
                    self.listWorkType(res);
                    dfd.resolve();
                }).always(() => nts.uk.ui.block.clear()); // clear block ui.

                return dfd.promise();
            }
        }
    }
}