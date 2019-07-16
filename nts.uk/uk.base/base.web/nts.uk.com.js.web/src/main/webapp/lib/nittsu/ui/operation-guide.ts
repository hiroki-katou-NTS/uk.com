/// <reference path="../reference.ts"/>

module nts.uk.ui.guide {

    const ROW_HEIGHT = 20;
    
    module resource {
        export let linkHide = "操作ガイド　非表示";
        export let linkShow = "操作ガイド　表示";
    }
    
    export function operate(path: string, page: Page) {
        nts.uk.request.ajax("com", path).done(config => {
            let op = new OperationGuide(config);
            op.setPosition(page);
        });
    }
    
    class OperationGuide {
        isUsed: boolean;
        display: boolean;
        lineCount: number;
        content: string;
        
        constructor(config: any) {
            this.isUsed = config.isUsed;
            this.lineCount = config.lineCount;
            this.content = config.content;
        }
        
        link(top: number) {
            let self = this;
            self.display = true;
            let $link = $("<a/>").addClass("nts-guide-link").text(resource.linkHide);
            $link.css("margin-top", top);
            $link.on("click", () => {
                if (self.display) {
                    $(".nts-guide-link").text(resource.linkShow);
                    $(".nts-guide-area").hide();
                    self.display = !self.display;
                    return;
                }
                
                $(".nts-guide-link").text(resource.linkHide);
                $(".nts-guide-area").show();
                self.display = !self.display;
            });
            
            return $link;
        }
        
        textArea() {
            let self = this;
            let $area = $("<div/>").addClass("nts-guide-area");
            $area.height(ROW_HEIGHT * self.lineCount);
            let content = self.content.split('\n').join("<br/>");
            $area.html(content);
            
            return $area;
        }
        
        setPosition(page: Page) {
            let self = this;
            switch (page) {
                case Page.NORMAL:
                default:
                    let $functionsArea = $("#functions-area");
                    if ($functionsArea.length == 0) return;
                    if ($functionsArea.find(".nts-guide-link").length == 0) {
                        let top = ($functionsArea.height() - 18) / 2;
                        $functionsArea.append(self.link(top));
                    }
                    
                    if (!$functionsArea.next().is(".nts-guide-area")) {
                        $functionsArea.after(self.textArea());
                    }
                    
                    break;
                case Page.SIDEBAR:
                    let $contentHeader = $(".sidebar-content-header");
                    if ($contentHeader.length > 0) {
                        $contentHeader.each(function() { 
                            let $header = $(this);
                            if ($header.find(".nts-guide-link").length == 0) {
                                let top = ($header.height() - 18) / 2;
                                $header.append(self.link(top));
                            }
                        });
                        
                        $contentHeader.each(function() {
                            let $header = $(this);
                            if (!$header.next().is(".nts-guide-area")) {
                                $header.after(self.textArea()); 
                            }
                        });
                    }
                    
                    break;
                case Page.FREE_LAYOUT:
                    break;
            }
        }
    }
    
    enum Page {
        NORMAL = 0,
        SIDEBAR = 1,
        FREE_LAYOUT = 2
    }
}
