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
            itemList: KnockoutObservableArray<Infor> = ko.observableArray( [] );
            selectedCode: KnockoutObservable<string> = ko.observable( '' );
            checked: KnockoutObservable<boolean> = ko.observable( false );
            target: KnockoutObservable<number> = ko.observable( 0 );
            targetID: KnockoutObservable<string>;

            constructor() {
                let self = this;

                //target: 会社:2/職場:0/職場グループ:1
                let dataShare = getShared( 'dataForJD' );
                self.target = ko.observable( dataShare.target );
                self.targetID = ko.observable( dataShare.targetID );
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

                //「会社」の場合
                if ( self.target() == 2 ) {
                    service.getShiftPaletteByCompany().done(( data ) => {
                        let shiftPaletData = [];
                        let page: string;
                        self.orgName( '' );
                        _.sortBy( data, 'page' ).forEach( e => {
                            if ( e.name != null ) {
                                page = getText( 'KSU001_110' ) + e.page + getText( 'KSU001_161' );
                                shiftPaletData.push( new Infor( page, page + e.name ) );
                            } else {
                                page = getText( 'KSU001_110' ) + e.page;
                                shiftPaletData.push( new Infor( page, page ) );
                            }
                        } );
                        self.itemList( shiftPaletData );
                        dfd.resolve();
                    } );
                }
                //職場　の場合
                else if ( self.target() == 0 ) {
                    service.getShiftPaletteByWP(self.targetID()).done(( data ) => {
                        let shiftPaletData = [];
                        let page: string;
                        self.orgName( '' );
                        _.sortBy( data, 'page' ).forEach( e => {
                            if ( e.name != null ) {
                                page = getText( 'KSU001_110' ) + e.page + getText( 'KSU001_161' );
                                shiftPaletData.push( new Infor( page, page + e.name ) );
                            } else {
                                page = getText( 'KSU001_110' ) + e.page;
                                shiftPaletData.push( new Infor( page, page ) );
                            }
                        } );
                        self.itemList( shiftPaletData );
                        dfd.resolve();
                    } );
                }
                //職場グループ　の場合
                else if ( self.target() == 1 ) {
                    service.getShiftPaletteByWPG(self.targetID()).done(( data ) => {
                        let shiftPaletData = [];
                        let page: string;
                        self.orgName( '' );
                        _.sortBy( data, 'page' ).forEach( e => {
                            if ( e.name != null ) {
                                page = getText( 'KSU001_110' ) + e.page + getText( 'KSU001_161' );
                                shiftPaletData.push( new Infor( page, page + e.name ) );
                            } else {
                                page = getText( 'KSU001_110' ) + e.page;
                                shiftPaletData.push( new Infor( page, page ) );
                            }
                        } );
                        self.itemList( shiftPaletData );
                        dfd.resolve();
                    } );
                }
                return dfd.promise();
            }

        }

        export class Infor {
            page: string;
            name: string;
            constructor( page: string, name: string ) {
                let self = this;
                self.page = page;
                self.name = name;
            }
        }
    }
}