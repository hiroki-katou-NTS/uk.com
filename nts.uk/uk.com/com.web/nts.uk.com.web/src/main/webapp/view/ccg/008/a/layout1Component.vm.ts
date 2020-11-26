/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg008.a.Layout1ComponentViewModel {

  @component({
    name: 'layout1-component',
    template: 
    `
        <div class="panel panel-frame">
          <com:ko-if bind="$component.isShowUrlLayout1()">
            <iframe class="iframe_fix" id="preview-iframe1" data-bind="attr:{src: $component.urlIframe1}"></iframe>
          </com:ko-if>
          <div data-bind="foreach: $component.lstHtml">
            <div data-bind="html: html" id="F1-frame" ></div>
          </div>
        </div>
    `
  })
  export class Layout1ComponentViewModel extends ko.ViewModel {
    urlIframe1: KnockoutObservable<string> = ko.observable("");
    lstHtml: KnockoutObservableArray<string> = ko.observableArray([]);
    isShowUrlLayout1: KnockoutObservable<boolean> = ko.observable(false);
    created(param: any) {
      const vm = this;
      const data = param.item();
      const layout1 = param.item().layout1;
      if (layout1) {
        if (data.urlLayout1) {
          vm.isShowUrlLayout1(true);
          vm.urlIframe1(data.urlLayout1);
        } else {
          const lstFileId = ko.observableArray([]);
          _.each(layout1, (item: any) => {
            const fileId = item.fileId;
            lstFileId().push(fileId);
          });
          const param = {
            lstFileId: lstFileId(),
          };
          vm.$ajax("com", 'sys/portal/createflowmenu/extractListFileId', param).then((res: any) => {
            const mappedList: any = _.map(res, (item: any) => {
              const width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
              const height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
              return {html: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>`};
            });
            vm.lstHtml(mappedList);
          });
        }
      }
    }

  }
}