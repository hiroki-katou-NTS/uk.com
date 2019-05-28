##### 1. サンプル
<div class="navbar bg-primary" style="padding-left: 15px">
    <div class="row">
        <div class="col-md-9">
            <button type="button" class="btn btn-link"><i class="fa fa-save"></i></button>
            <button type="button" class="btn btn-link">Middle</button>
            <button type="button" class="btn btn-link">Right</button>
        </div>
        <div class="col-md-3">
            <div class="input-group input-group-transparent input-group-search">
                <div class="input-group-append">
                    <span class="input-group-text fa fa-search"></span>
                </div>
                <input type="text" class="form-control" />
            </div>
        </div>
    </div>
</div>
<br />

##### 2. 説明
`toolbar`は`directive`である。  
Toolbarを作るために`v-toolbar`を`nav`や`div`につけてください。

##### 3. コード
```html
<div v-toolbar>
    <div class="row">
        <div class="col-md-9">
            <button type="button" class="btn btn-link">
                <i class="fa fa-save"></i>
            </button>
            <button type="button" class="btn btn-link">Middle</button>
            <button type="button" class="btn btn-link">Right</button>
        </div>
        <div class="col-md-3">
            <div class="input-group input-group-transparent input-group-search">
                <div class="input-group-append">
                    <span class="input-group-text fa fa-search"></span>
                </div>
                <input type="text" class="form-control" />
            </div>
        </div>
    </div>
</div>
```