## HTML (en)
```html
<!doctype html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Marked in the browser</title>
</head>
<body>
  <div id="content"></div>
  <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
  <script>
    document.getElementById('content').innerHTML =
      marked('# Marked in the browser\n\nRendered by **marked**.');
  </script>
</body>
</html>
```
### TypeScript
```typescript
import { Vue } from '@app/provider';
import { component } from '@app/core/component';

import { focus } from '@app/directives';
import { SampleComponent } from '@app/components';

import { LanguageBar } from '@app/plugins';

@component({
    route: '/',
    style: require('./style.scss'),
    template: require('./index.html'),
    resource: require('./resources.json'),
    directives: {
        'focus': focus
    },
    components: {
        'vuong': SampleComponent,
        'language-bar': LanguageBar
    },
    validations: {
        title: {
            minLength: 10,
        },
        resource: {
            required: true,
            alpha: true
        },
        model: {
            name: {
                required: true
            },
            addrs: {
                required: true,
                self_validator: {
                    test: /^\d{3,5}$/,
                    message: 'xxxx'
                }
            },
            address: {
                province: {
                    required: true
                },
                district: {
                    required: true
                }
            },
            office: {
                head: {
                    required: true
                }
            }
        }
    },
    markdown: require('./content.md')
})
export class HomeComponent extends Vue {
    title: string = 'home';

    resource: string = '';

    model = {
        name: '',
        addrs: 'world'
    };

    constructor() {
        super();
        let self = this;

        window['v'] = Vue;
        window['h'] = self;
    }

    alertNow() {
        this.$modal('vuong')
            .onClose((data: any) => {
                console.log(data);
            });
    }
}
```