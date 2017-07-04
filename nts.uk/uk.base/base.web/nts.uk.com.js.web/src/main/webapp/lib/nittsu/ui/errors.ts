/// <reference path="../reference.ts"/>

module nts.uk.ui.errors {

    export class ErrorsViewModel {
        title: string;
        errors: KnockoutObservableArray<ErrorListItem>;
        gridErrors: KnockoutObservableArray<GridCellError>;
        displayErrors: KnockoutObservableArray<any>;
        option: any;
        occurs: KnockoutComputed<boolean>;
        allResolved: JQueryCallback;
        allCellsResolved: JQueryCallback;

        constructor(dialogOptions?: any) {
            this.title = "エラー一覧"; 
            this.errors = ko.observableArray([]);
            this.errors.extend({ rateLimit: 1 });
            this.gridErrors = ko.observableArray([]);
            this.displayErrors = !util.isNullOrUndefined(dialogOptions) && dialogOptions.forGrid ? this.gridErrors : this.errors;
            this.option = ko.mapping.fromJS(new option.ErrorDialogOption(dialogOptions));
            this.occurs = ko.computed(() => this.errors().length !== 0 || this.gridErrors().length !== 0);
            this.allResolved = $.Callbacks();
            this.allCellsResolved = $.Callbacks();

            this.errors.subscribe(() => {
                if (this.errors().length === 0) {
                    this.allResolved.fire();
                }
            });

            this.allResolved.add(() => {
                this.hide();
            });
            
            // Grid
            this.gridErrors.subscribe(() => {
                if (this.gridErrors().length === 0) {
                    this.allCellsResolved.fire();
                }
            });
            
            this.allCellsResolved.add(() => {
                this.hide();
            });
        }

        closeButtonClicked() {
        }

        open() {
            this.option.show(true);
        }

        hide() {
            this.option.show(false);
        }

        addError(error: ErrorListItem) {
            var duplicate = _.filter(this.errors(), e => e.$control.is(error.$control) 
                            && (typeof error.message === "string" ? e.messageText === error.message : e.messageText === error.messageText));
            
            if (duplicate.length == 0) {
                if (typeof error.message === "string") {
                    error.messageText = error.message;
                } else {
                    //business exception
                    if (error.message.message) {
                        error.messageText = error.message.message;
                        error.errorCode = error.message.messageId != null && error.message.messageId.length > 0 ? error.message.messageId : "";
                    } else {
                        if (error.$control.length > 0) {
                            let controlNameId = error.$control.eq(0).attr("data-name");
                            if (controlNameId) {
                                error.messageText = nts.uk.resource.getMessage(error.message.messageId, nts.uk.resource.getText(controlNameId), error.message.messageParams);
                            } else {
                                error.messageText = nts.uk.resource.getMessage(error.message.messageId, error.message.messageParams);
                            }
                        } else {
                            error.messageText = nts.uk.resource.getMessage(error.message.messageId);

                        }
                        error.errorCode = error.message.messageId;
                    }

                }
                this.errors.push(error);
            }
        }

        hasError() {
            return this.errors().length > 0;
        }

        clearError() {
            $(".error").children().each((index, element) => {
                if ($(element).data("hasError"))
                    $(element).data("hasError", false);
            });
            $(".error").removeClass('error');
            this.errors.removeAll();
        }

        removeErrorByElement($element: JQuery) {
            this.errors.remove((error) => {
                return error.$control.is($element);
            });
        }
        
        getErrorByElement($element: JQuery): ErrorListItem {
            return _.find(this.errors(), e => { 
                return e.$control.is($element)
            });
        }
        
        // Grid errors
        addCellError(error: GridCellError) {
            let self = this;
            let exists = _.filter(this.gridErrors(), function(err: GridCellError) {
                return self.sameCells(error, err);
            });
            if (exists.length > 0) return;
            this.gridErrors.push(error);
        }
        
        removeCellError($grid: JQuery, rowId: any, columnKey: string) {
            this.gridErrors.remove(function(err: GridCellError) {
                return err.grid.is($grid) && err.rowId === rowId && err.columnKey === columnKey;
            });
        }
        
        gridHasError() {
            return this.gridErrors().length > 0;
        }
        
        sameCells(one: GridCellError, other: GridCellError) {
            if (!one.grid.is(other.grid)) return false;
            if (one.rowId !== other.rowId) return false;
            if (one.columnKey !== other.columnKey) return false;
            return true;
        }
    }

    export interface ErrorListItem {
        tab?: string;
        location: string;
        messageText: string;
        message: any;
        errorCode: string;
        $control?: JQuery;
    }
    
    export interface GridCellError {
        grid: JQuery;
        rowId: any;
        columnKey: string;
        message: string;
    }

    export class ErrorHeader {
        name: string;
        text: string;
        width: any;
        visible: boolean;

        constructor(name: string, text: string, width: any, visible: boolean) {
            this.name = name;
            this.text = text;
            this.width = width;
            this.visible = visible;
        }
    }

    /** 
     *  Public API
    **/
    export function errorsViewModel(): ErrorsViewModel {
        return nts.uk.ui._viewModel.kiban.errorDialogViewModel;
    }

    export function show(): void {
        errorsViewModel().open();
    }

    export function hide(): void {
        errorsViewModel().hide();
    }

    export function add(error: ErrorListItem): void {
        errorsViewModel().addError(error);
    }

    export function hasError(): boolean {
        return errorsViewModel().hasError();
    }

    export function clearAll(): void {
        errorsViewModel().clearError();
    }

    export function removeByElement($control: JQuery): void {
        errorsViewModel().removeErrorByElement($control);
    }
    
    export function getErrorByElement($element: JQuery): ErrorListItem{
        return errorsViewModel().getErrorByElement($element);    
    }
    
    // Grid errors
    export function addCell(error: GridCellError): void {
        errorsViewModel().addCellError(error);
    }
    
    export function removeCell($grid: JQuery, rowId: any, columnKey: string): void {
        errorsViewModel().removeCellError($grid, rowId, columnKey);
    }
    
    export function gridHasError(): boolean {
        return errorsViewModel().gridHasError();
    }
}