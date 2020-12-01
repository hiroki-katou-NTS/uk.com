/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg008.a.Layout1ComponentViewModel {

  @component({
    name: 'layout1-component',
    template: 
    `
        <div>
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
    lstHtml: KnockoutObservableArray<any> = ko.observableArray([]);
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
              return {html: `<iframe id="frameF1" ></iframe>`};
            });
            vm.lstHtml(mappedList);
            if (!_.isEmpty(res)) {
              vm.renderHTML(res[0].htmlContent);
            }
          });
        }
      }
    }

    private renderHTML(htmlSrc: string) {
      const vm = this;
      const $iframe = $("#frameF1");
      // If browser supports srcdoc for iframe
      // then add src to srcdoc attr
      if ("srcdoc" in $iframe) {
        $iframe.attr("srcdoc", htmlSrc);
      } else {
        // Fallback to IE... (doesn't support srcdoc)
        // Write directly into iframe body
        const ifr = document.getElementById('frameF1');
        const iframedoc = (ifr as any).contentDocument || (ifr as any).contentWindow.document;
        iframedoc.body.innerHTML = htmlSrc;
        const width = iframedoc.activeElement.scrollWidth;
        const height = iframedoc.activeElement.scrollHeight;
        (ifr as any).width = `${width.toString()}px`;
        (ifr as any).height = `${height.toString()}px`;
      }
    }
  }
}