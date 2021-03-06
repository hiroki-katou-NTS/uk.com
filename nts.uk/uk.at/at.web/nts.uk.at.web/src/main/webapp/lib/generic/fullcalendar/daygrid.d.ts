module FullCalendar {
    abstract class TableView<State = Dictionary> extends DateComponent<ViewProps, State> {
        protected headerElRef: RefObject<HTMLTableCellElement>;
        renderSimpleLayout(headerRowContent: ChunkConfigRowContent, bodyContent: (contentArg: ChunkContentCallbackArgs) => VNode): createElement.JSX.Element;
        renderHScrollLayout(headerRowContent: ChunkConfigRowContent, bodyContent: (contentArg: ChunkContentCallbackArgs) => VNode, colCnt: number, dayMinWidth: number): createElement.JSX.Element;
    }

    class DayTableView extends TableView {
        private buildDayTableModel;
        private headerRef;
        private tableRef;
        render(): createElement.JSX.Element;
    }

    function buildDayTableModel(dateProfile: DateProfile, dateProfileGenerator: DateProfileGenerator): DayTableModel;

    interface TableSeg extends Seg {
        row: number;
        firstCol: number;
        lastCol: number;
    }

    interface TableCellModel {
        key: string;
        date: DateMarker;
        extraHookProps?: Dictionary;
        extraDataAttrs?: Dictionary;
        extraClassNames?: string[];
    }

    interface MoreLinkArg {
        date: DateMarker;
        allSegs: TableSeg[];
        hiddenSegs: TableSeg[];
        moreCnt: number;
        dayEl: HTMLElement;
        ev: VUIEvent;
    }

    interface MoreLinkContentArg {
        num: number;
        text: string;
        view: ViewApi;
    }

    type MoreLinkMountArg = MountArg<MoreLinkContentArg>;

    interface EventSegment {
        event: EventApi;
        start: Date;
        end: Date;
        isStart: boolean;
        isEnd: boolean;
    }

    type MoreLinkAction = MoreLinkSimpleAction | MoreLinkHandler;
    type MoreLinkSimpleAction = 'popover' | 'week' | 'day' | 'timeGridWeek' | 'timeGridDay' | string;

    interface MoreLinkArg$1 {
        date: Date;
        allDay: boolean;
        allSegs: EventSegment[];
        hiddenSegs: EventSegment[];
        jsEvent: VUIEvent;
        view: ViewApi;
    }
    type MoreLinkHandler = (arg: MoreLinkArg$1) => MoreLinkSimpleAction | void;

    const OPTION_REFINERS: {
        moreLinkClick: Identity<MoreLinkAction>;
        moreLinkClassNames: Identity<ClassNamesGenerator<MoreLinkContentArg>>;
        moreLinkContent: Identity<CustomContentGenerator<MoreLinkContentArg>>;
        moreLinkDidMount: Identity<DidMountHandler<MountArg<MoreLinkContentArg>>>;
        moreLinkWillUnmount: Identity<WillUnmountHandler<MountArg<MoreLinkContentArg>>>;
    };

    type ExtraOptionRefiners = typeof OPTION_REFINERS;

    interface BaseOptionRefiners extends ExtraOptionRefiners {
    }

    interface DayTableProps {
        dateProfile: DateProfile;
        dayTableModel: DayTableModel;
        nextDayThreshold: Duration;
        businessHours: EventStore;
        eventStore: EventStore;
        eventUiBases: EventUiHash;
        dateSelection: DateSpan | null;
        eventSelection: string;
        eventDrag: EventInteractionState | null;
        eventResize: EventInteractionState | null;
        colGroupNode: VNode;
        tableMinWidth: CssDimValue;
        renderRowIntro?: () => VNode;
        dayMaxEvents: boolean | number;
        dayMaxEventRows: boolean | number;
        expandRows: boolean;
        showWeekNumbers: boolean;
        headerAlignElRef?: RefObject<HTMLElement>;
        clientWidth: number | null;
        clientHeight: number | null;
        forPrint: boolean;
    }

    class DayTable extends DateComponent<DayTableProps, ViewContext> {
        private slicer;
        private tableRef;
        render(): createElement.JSX.Element;
        handleRootEl: (rootEl: HTMLDivElement | null) => void;
        prepareHits(): void;
        queryHit(positionLeft: number, positionTop: number): Hit;
    }

    class DayTableSlicer extends Slicer<TableSeg, [DayTableModel]> {
        forceDayIfListItem: boolean;
        sliceRange(dateRange: DateRange, dayTableModel: DayTableModel): TableSeg[];
    }

    interface TableProps {
        elRef?: Ref<HTMLDivElement>;
        dateProfile: DateProfile;
        cells: TableCellModel[][];
        renderRowIntro?: () => VNode;
        colGroupNode: VNode;
        tableMinWidth: CssDimValue;
        expandRows: boolean;
        showWeekNumbers: boolean;
        clientWidth: number | null;
        clientHeight: number | null;
        businessHourSegs: TableSeg[];
        bgEventSegs: TableSeg[];
        fgEventSegs: TableSeg[];
        dateSelectionSegs: TableSeg[];
        eventSelection: string;
        eventDrag: EventSegUiInteractionState | null;
        eventResize: EventSegUiInteractionState | null;
        dayMaxEvents: boolean | number;
        dayMaxEventRows: boolean | number;
        headerAlignElRef?: RefObject<HTMLElement>;
        forPrint: boolean;
    }

    interface TableState {
        morePopoverState: MorePopoverState | null;
    }

    interface MorePopoverState extends MoreLinkArg {
        currentFgEventSegs: TableSeg[];
    }

    class Table extends DateComponent<TableProps, TableState> {
        private splitBusinessHourSegs;
        private splitBgEventSegs;
        private splitFgEventSegs;
        private splitDateSelectionSegs;
        private splitEventDrag;
        private splitEventResize;
        private buildBuildMoreLinkText;
        private rootEl;
        private rowRefs;
        private rowPositions;
        private colPositions;
        state: {
            morePopoverState: any;
        };
        render(): createElement.JSX.Element;
        handleRootEl: (rootEl: HTMLElement | null) => void;
        handleMoreLinkClick: (arg: MoreLinkArg) => void;
        handleMorePopoverClose: () => void;
        prepareHits(): void;
        positionToHit(leftPosition: any, topPosition: any): {
            row: any;
            col: any;
            dateSpan: {
                range: {
                    start: Date;
                    end: Date;
                };
                allDay: boolean;
            };
            dayEl: HTMLTableCellElement;
            relativeRect: {
                left: any;
                right: any;
                top: any;
                bottom: any;
            };
        };
        private getCellEl;
        private getCellRange;
    }
}