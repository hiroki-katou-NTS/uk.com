module test.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        input: any;
        fillter: KnockoutObservable<boolean>;
        treeGrid: TreeComponentOption;
        selectedWorkplaceId: KnockoutObservable<string>;
        baseDate: KnockoutObservable<Date>;
        currentScreen: any = null;
        showNone: KnockoutObservable<boolean> = ko.observable( false );
        showDeferred: KnockoutObservable<boolean> = ko.observable( false );
        initiallySelected: KnockoutObservable<string> = ko.observable( '' );
        disabled: KnockoutObservable<boolean>;
        widthValue: KnockoutObservable<number> = ko.observable( null );
        option: any;
        constructor() {
            let self = this;
            self.fillter = ko.observable( false );
            self.disabled = ko.observable( false );
            self.input = [];
            self.baseDate = ko.observable( new Date() );
            self.selectedWorkplaceId = ko.observable( '' );
            self.option = new nts.uk.ui.option.NumberEditorOption( {
                width: "",
                textalign: "left"
            } );
            self.treeGrid = {
                isMultipleUse: true,
                isMultiSelect: false,
                treeType: 1,
                selectedId: self.selectedWorkplaceId,
                baseDate: self.baseDate,
                selectType: 4,
                isShowSelectButton: false,
                isDialog: false,
                maxRows: 15,
                tabindex: 1,
                systemType: 2

            };
            $( '#tree-grid' ).ntsTreeComponent( self.treeGrid ).done( function() {
                let workplaceGridList = $( '#tree-grid' ).getDataList();
                if ( workplaceGridList.length > 0 ) {
                    self.selectedWorkplaceId( workplaceGridList[0].id );
                }
            } );
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        openDialog() {
            let self = this;
            let data: any = {};

            data.width = self.widthValue() != null && self.widthValue().toString() != "" ? self.widthValue() : 500;
            data.tabIndex = 1;
            data.filter = self.fillter();
            data.disabled = self.disabled();
            data.workplaceId = self.selectedWorkplaceId();
            data.selected = self.initiallySelected();
            data.dataSources = [];
            let showMode = 0;
            if ( self.showNone() && !self.showDeferred() ) {
                showMode = 1;
            }
            if ( !self.showNone() && self.showDeferred() ) {
                showMode = 2;
            }
            if ( self.showNone() && self.showDeferred() ) {
                showMode = 3;
            }
            data.showMode = showMode;

            setShare( 'data', data );


            self.currentScreen = nts.uk.ui.windows.sub.modal( "/view/kcp/013/test/dialog.xhtml" );
        }
    }
}