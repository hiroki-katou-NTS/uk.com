@component({
    name: 'kdp-001-b-widget-frame',
    template: `<div data-bind="widget-content: 405, src: '/nts.uk.at.web/view/kdp/001/a/index.xhtml?mode=b'"></div>`
})
export class WidgetFrameComponent extends ko.ViewModel {
    widget: string = 'KDP001_B_WF';
}