module nts.uk.pr.view.qpp021.h {

    import option = nts.uk.ui.option;
    import SocialInsuranceOfficeImportDto = service.model.SocialInsuranceOfficeImportDto;

    export module viewmodel {
        export class ScreenModel {

            igGrid: any;
            igGridDataSource: KnockoutObservableArray<ItemViewModel>;

            constructor() {
                var self = this;
                var item: ItemViewModel;
                item = new ItemViewModel();
                item.uuid ='12321323223';
                item.groupName = 'NAME';
                item.name = 'name';
                item.amount = ko.observable(6);
                item.groupCalTypeText = '323ds2f3d';
                var itemarr : ItemViewModel[];
                itemarr = [];
                itemarr.push(item);
               self.igGridDataSource = ko.observableArray(itemarr);
                self.initIgGrid();
                
            }

            //start page
            startPage(): JQueryPromise<this> {
                var self = this;
                var dfd = $.Deferred<this>();
                dfd.resolve(self);
                return dfd.promise();
            }


            initIgGrid(): void {
                var self = this;

                // IgGrid
                self.igGrid = ko.observable({
                    dataSource: self.igGridDataSource,
                    width: '100%',
                    primaryKey: 'uuid',
                    height: '250px',
                    features: [
                        {
                            name: 'Updating',
                            editMode: 'row',
                            enableAddRow: false,
                            excelNavigatorMode: false,
                            enableDeleteRow: false,
                            columnSettings: [
                                {
                                    columnKey: 'uuid',
                                    readOnly: true
                                },
                                {
                                    columnKey: 'name',
                                    readOnly: true
                                },
                                {
                                    columnKey: 'amount',
                                    editorOptions: {
                                        constraint: 'WtValue',
                                        option: {
                                        },
                                        required: true
                                    },
                                    readOnly: false
                                }
                            ],
                        }
                    ],
                    autoCommit: true,
                    columns: [
                        { headerText: 'Element Name', dataType: 'string', key: 'uuid', hidden: true },
                        { headerText: 'コード', dataType: 'string', key: 'code', width: '10%', columnCssClass: "bgIgCol" },
                        { headerText: '名称', dataType: 'string', key: 'name', width: '10%', columnCssClass: "bgIgCol" },
                        { headerText: '今月の給与明細書に印刷する連絡事項', dataType: 'text', key: 'amount', width: '40%', columnCssClass: "halign-right" },
                        { headerText: '毎月の給与明細書に印刷する連絡事項', dataType: 'text', key: 'amount', width: '40%', columnCssClass: "halign-right" }
                    ]
                });
            }
        }

        class ItemViewModel {
            uuid: string;
            groupName: string;
            name: string;
            amount: KnockoutObservable<number>;
            groupCalTypeText: string;

            /**
             * Constructor.
             */
        }
    }
}