module test.viewmodel {

    import setShared = nts.uk.ui.windows.setShared;
    import IDataTransfer = nts.uk.at.view.kdl044.a.viewmodel.IDataTransfer;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        isMultiSelect: KnockoutObservable<boolean> = ko.observable( true );
        listShifuto: KnockoutObservableArray<Shifuto> = ko.observableArray( [] );
        columns: KnockoutObservableArray<any>;
        selectedCodes: KnockoutObservableArray<String> = ko.observableArray( [] );
        modeList: KnockoutObservableArray<any>;
        permissionList: KnockoutObservableArray<any>;
        selectedMode: KnockoutObservable<number>;
        selectedPer: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        filterList: KnockoutObservableArray<any>;
        selectedFilter: KnockoutObservable<number>;
        listFilter: KnockoutObservableArray<Shifuto> = ko.observableArray( [] );
        selectedFilterCodes: KnockoutObservableArray<String> = ko.observableArray( [] );
        filterColumns: KnockoutObservableArray<any>;

        constructor() {
            let self = this;

            self.columns = ko.observableArray( [
                { headerText: getText( 'KDL044_2' ), key: "code", dataType: "string", width: 50 },
                { headerText: getText( 'KDL044_3' ), key: "name", dataType: "string", width: 100 },
                { headerText: getText( 'KDL044_4' ), key: "workType", dataType: "string", width: 100 },
                { headerText: getText( 'KDL044_5' ), key: "workTime", dataType: "string", width: 100 },
                { headerText: getText( 'KDL044_6' ), key: "time1", dataType: "string", width: 150 },
                { headerText: getText( 'KDL044_7' ), key: "time2", dataType: "string", width: 150 },
                { headerText: getText( 'KDL044_8' ), key: "remark", dataType: "string", width: 200 }
            ] );

            self.filterColumns = ko.observableArray( [
                { headerText: getText( 'KDL044_2' ), key: "code", dataType: "string", width: 50 },
                { headerText: getText( 'KDL044_3' ), key: "name", dataType: "string", width: 100 }
            ] );
            self.modeList = ko.observableArray( [
                new BoxModel( 1, '複数選択' ),
                new BoxModel( 2, '単一選択' )
            ] );
            self.permissionList = ko.observableArray( [
                new BoxModel( 1, '選択肢[なし]を表示する' ),
                new BoxModel( 0, '選択肢[なし]を表示しない' )
            ] );
            self.filterList = ko.observableArray( [
                new BoxModel( 0, '絞り込みしない' ),
                new BoxModel( 1, '職場' ),
                new BoxModel( 2, '職場グループ' )
            ] );
            self.selectedMode = ko.observable( 1 );
            self.selectedPer = ko.observable( 1 );
            self.selectedFilter = ko.observable( 0 );
            self.enable = ko.observable( true );
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let shifutos: Array<Shifuto> = [];
            for ( let i = 13; i >= 0; i-- ) {
                shifutos.push( new Shifuto(
                    i.toString(),
                    i.toString(),
                    i.toString(),
                    i.toString(),
                    i.toString(),
                    i.toString(),
                    i.toString()
                ) );
            }
            self.listShifuto( _.sortBy( shifutos, [function( o ) { return new Number( o.code ); }] ) );
            let filter: Array<Filter> = [];
            for ( let i = 13; i >= 0; i-- ) {
                filter.push( new Filter(
                    i.toString(),
                    i.toString()
                ) );
            }
            self.listFilter( _.sortBy( filter, [function( o ) { return new Number( o.code ); }] ) );
            dfd.resolve();

            return dfd.promise();
        }
        openDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            let dataSetShare: IDataTransfer = {
                isMultiSelect: self.selectedMode() == 1 ? true : false,
                permission: self.selectedPer() == 1 ? true : false,
                filter: self.selectedFilter(),
                filterIDs: self.selectedFilterCodes(),
                shifutoCodes: self.selectedCodes()
            }
            setShared( 'kdl044Data', dataSetShare );
            nts.uk.ui.windows.sub.modal( "/view/kdl/044/a/index.xhtml", { dialogClass: "no-close" } )
                .onClosed(() => {
                    let isCancel = getShared( 'kdl044_IsCancel' ) != null? getShared( 'kdl044_IsCancel' ): true;
                    if ( !isCancel ) {
                        let returnedData = getShared( 'kdl044ShifutoCodes' );
                        self.selectedCodes( returnedData );
                    }
                    nts.uk.ui.block.clear();
                } );
        }
    }

    class Shifuto {
        code: string;
        name: string;
        workType: string;
        workTime: string;
        time1: string;
        time2: string;
        remark: string;
        constructor( code: string,
            name: string,
            workType: string,
            workTime: string,
            time1: string,
            time2: string,
            remark: string ) {
            let self = this;
            self.code = code;
            self.name = name;
            self.workType = workType;
            self.workTime = workTime;
            self.time1 = time1;
            self.time2 = time2;
            self.remark = remark;
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor( id, name ) {
            let self = this;
            self.id = id;
            self.name = name;
        }
    }

    class Filter {
        code: string;
        name: string;
        constructor( code, name ) {
            let self = this;
            self.code = code;
            self.name = name;
        }
    }

}