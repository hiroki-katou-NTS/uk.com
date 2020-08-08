module nts.uk.at.view.ksu001.jd {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export module viewmodel {
        export class ScreenModel {
            //元名称表示
            orgName: KnockoutObservable<string> = ko.observable( '' );
            //先名称入力
            desName: KnockoutObservable<string> = ko.observable( '' );
            itemList: KnockoutObservableArray<any> = ko.observableArray( [{code: '1', name: '1'}] );
            selectedCode: KnockoutObservable<string> = ko.observable( '' );
            checked: KnockoutObservable<boolean> = ko.observable( true );

            constructor() {
                let self = this;

            }

            /**
             * decision
             */
            decision(): void {
                let self = this;

                setShared( "dataFromJB", {
                    text: self.textName(),
                    tooltip: tooltip,
                    data: arrData
                } );

                nts.uk.ui.windows.close();
            }

            /**
             * Close dialog
             */
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();

                let dataShare = getShared( 'dataForJD' )
                //target = 0 -> workplace, target = 1 -> workplaceGroup, target = 2 -> company 
//                let datas = {
//                    page: dataShare.page,
//                    target: dataShare.target,
//                    organization: dataShare.organization
//                }
//
//                service.getShiftMasterWorkInfo( datas ).done(( data ) => {
//                    data.unshift( { shiftMasterName: nts.uk.resource.getText( "KSU001_98" ), shiftMasterCode: "", workTime1: "", workTime2: "", remark: "" } );
//                    if ( data ) {
//                        for ( let i = 0; i < data.length; i++ ) {
//                            data[i].workTime1 = data[i].workTime1 + " " + data[i].workTime2;
//                        }
//                    }
//                    self.listWorkType( _.sortBy( data, ['shiftMasterCode'] ) );
//                } ).fail(( res: any ) => {
//                    nts.uk.ui.dialog.alert( { messageId: res.messageId } );
//                } );
                dfd.resolve();
                return dfd.promise();
            }

        }
        interface IData {
            code: string,
            name: string
        }

        export class InforError {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            constructor( param: IData ) {
                let self = this;
                self.code = ko.observable( param.code );
                self.name = ko.observable( param.name );
            }
        }
    }
}