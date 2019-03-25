# Buttons

##### Common
<button type="button" class="btn btn-primary">Primary</button>
<button type="button" class="btn btn-secondary">Secondary</button>
<button type="button" class="btn btn-success">Success</button>
<button type="button" class="btn btn-info">Info</button>
<button type="button" class="btn btn-warning">Warning</button>
<button type="button" class="btn btn-danger">Danger</button>
<button type="button" class="btn btn-link">Link</button>

##### Code
```html
<button type="button" class="btn btn-primary">Primary</button>
<button type="button" class="btn btn-secondary">Secondary</button>
<button type="button" class="btn btn-success">Success</button>
<button type="button" class="btn btn-info">Info</button>
<button type="button" class="btn btn-warning">Warning</button>
<button type="button" class="btn btn-danger">Danger</button>
<button type="button" class="btn btn-link">Link</button>
```
---
##### Disabled
<button type="button" class="btn btn-primary" disabled>Primary</button>
<button type="button" class="btn btn-secondary" disabled>Secondary</button>
<button type="button" class="btn btn-success" disabled>Success</button>
<button type="button" class="btn btn-info" disabled>Info</button>
<button type="button" class="btn btn-warning" disabled>Warning</button>
<button type="button" class="btn btn-danger" disabled>Danger</button>
<button type="button" class="btn btn-link" disabled>Link</button>

##### Code
```html
<button type="button" class="btn btn-primary" disabled>Primary</button>
<button type="button" class="btn btn-secondary" disabled>Secondary</button>
<button type="button" class="btn btn-success" disabled>Success</button>
<button type="button" class="btn btn-info" disabled>Info</button>
<button type="button" class="btn btn-warning" disabled>Warning</button>
<button type="button" class="btn btn-danger" disabled>Danger</button>
<button type="button" class="btn btn-link" disabled>Link</button>
```
---
##### Outline
<button type="button" class="btn btn-outline-primary">Primary</button>
<button type="button" class="btn btn-outline-secondary">Secondary</button>
<button type="button" class="btn btn-outline-success">Success</button>
<button type="button" class="btn btn-outline-info">Info</button>
<button type="button" class="btn btn-outline-warning">Warning</button>
<button type="button" class="btn btn-outline-danger">Danger</button>
##### Code
```html
<button type="button" class="btn btn-outline-primary">Primary</button>
<button type="button" class="btn btn-outline-secondary">Secondary</button>
<button type="button" class="btn btn-outline-success">Success</button>
<button type="button" class="btn btn-outline-info">Info</button>
<button type="button" class="btn btn-outline-warning">Warning</button>
<button type="button" class="btn btn-outline-danger">Danger</button>
```
---
##### Dropdown buttons
<div class="btn-group mb-3">
    <button type="button" class="btn btn-primary">Primary</button>
    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle"></button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="javascript:void(0)">Dropdown link</a>
            <a class="dropdown-item" href="javascript:void(0)">Dropdown link</a>
        </div>
    </div>
</div>

##### Code
```html
<div class="btn-group">
    <button type="button" class="btn btn-primary">Primary</button>
    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle"></button>
        <div class="dropdown-menu">
            <a class="dropdown-item" href="javascript:void(0)">Dropdown link</a>
            <a class="dropdown-item" href="javascript:void(0)">Dropdown link</a>
        </div>
    </div>
</div>
```
---
##### Button size
<button type="button" class="btn btn-primary btn-lg">Large button</button>
<button type="button" class="btn btn-primary">Default button</button>
<button type="button" class="btn btn-primary btn-sm">Small button</button>

##### Code
```html
<button type="button" class="btn btn-primary btn-lg">Large button</button>
<button type="button" class="btn btn-primary">Default button</button>
<button type="button" class="btn btn-primary btn-sm">Small button</button>
```
---
##### Block button (full width)
<button type="button" class="btn btn-primary btn-block">Block level button</button>
##### Code
```html
<button type="button" class="btn btn-primary btn-block">Block level button</button>
```
---
##### Checkbox group
<div class="btn-group btn-group-toggle mb-3">
    <label class="btn btn-primary active">
        <input type="checkbox" checked="" autocomplete="off"> Active
    </label>
    <label class="btn btn-primary active">
        <input type="checkbox" autocomplete="off"> Check
    </label>
    <label class="btn btn-primary" >
        <input type="checkbox" autocomplete="off"> Check
    </label>
</div>

##### Code
```html
<div class="btn-group btn-group-toggle">
    <label class="btn btn-primary active" >
        <input type="checkbox" checked autocomplete="off" /> Active
    </label>
    <label class="btn btn-primary active">
        <input type="checkbox" autocomplete="off" /> Check
    </label>
    <label class="btn btn-primary">
        <input type="checkbox" autocomplete="off" /> Check
    </label>
</div>
```

##### Switch button
<div class="btn-group btn-group-toggle mb-3">
    <label class="btn btn-primary active">
        <input type="radio" name="options" autocomplete="off" checked /> Active
    </label>
    <label class="btn btn-primary">
        <input type="radio" name="options" /> Radio
    </label>
    <label class="btn btn-primary">
        <input type="radio" name="options" autocomplete="off" /> Radio
    </label>
</div>

##### Code
```html
<div class="btn-group btn-group-toggle mb-3">
    <label class="btn btn-primary active">
        <input type="radio" name="options" autocomplete="off" checked /> Active
    </label>
    <label class="btn btn-primary">
        <input type="radio" name="options" /> Radio
    </label>
    <label class="btn btn-primary">
        <input type="radio" name="options" autocomplete="off" /> Radio
    </label>
</div>
```

##### Tool bar (group buttons)
<div class="btn-toolbar">
    <div class="btn-group mr-4 mb-3">
        <button type="button" class="btn btn-secondary">New</button>
        <button type="button" class="btn btn-primary">Save</button>
        <button type="button" class="btn btn-danger">Delete</button>
        <button type="button" class="btn btn-secondary">Open dialog</button>
    </div>
    <div class="btn-group mr-4 mb-3">
        <button type="button" class="btn btn-secondary">New</button>
        <button type="button" class="btn btn-primary">Save</button>
        <button type="button" class="btn btn-danger">Delete</button>
        <button type="button" class="btn btn-secondary">Open dialog</button>
    </div>
    <div class="btn-group mb-3">
        <button type="button" class="btn btn-secondary">New</button>
        <button type="button" class="btn btn-primary">Save</button>
        <button type="button" class="btn btn-danger">Delete</button>
        <button type="button" class="btn btn-secondary">Open dialog</button>
    </div>
</div>

##### Code
```html
<div class="btn-toolbar">
    <!-- group 1 -->
    <div class="btn-group mr-4">
        <button type="button" class="btn btn-secondary">New</button>
        <button type="button" class="btn btn-primary">Save</button>
        <button type="button" class="btn btn-danger">Delete</button>
        <button type="button" class="btn btn-secondary">Open dialog</button>
    </div>
    <!-- group 2 -->
    <div class="btn-group mr-4">
        <button type="button" class="btn btn-secondary">New</button>
        <button type="button" class="btn btn-primary">Save</button>
        <button type="button" class="btn btn-danger">Delete</button>
        <button type="button" class="btn btn-secondary">Open dialog</button>
    </div>
    <!-- group 3 -->
    <div class="btn-group">
        <button type="button" class="btn btn-secondary">New</button>
        <button type="button" class="btn btn-primary">Save</button>
        <button type="button" class="btn btn-danger">Delete</button>
        <button type="button" class="btn btn-secondary">Open dialog</button>
    </div>
</div>
```
---
# Table

##### Hoverable
<table class="table table-hover">
    <thead>
        <tr>
            <th scope="col">Type</th>
            <th scope="col">Column heading</th>
            <th scope="col">Column heading</th>
            <th scope="col">Column heading</th>
        </tr>
    </thead>
    <tbody>
        <tr class="table-active">
            <th scope="row">Active</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr>
            <th scope="row">Default</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-primary">
            <th scope="row">Primary</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-secondary">
            <th scope="row">Secondary</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-success">
            <th scope="row">Success</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-danger">
            <th scope="row">Danger</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-warning">
            <th scope="row">Warning</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-info">
            <th scope="row">Info</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-light">
            <th scope="row">Light</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-dark">
            <th scope="row">Dark</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
    </tbody>
</table>

##### Code
```html
<table class="table table-hover">
    <thead>
        <tr>
            <th scope="col">Type</th>
            <th scope="col">Column heading</th>
            <th scope="col">Column heading</th>
            <th scope="col">Column heading</th>
        </tr>
    </thead>
    <tbody>
        <tr class="table-active">
            <th scope="row">Active</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr>
            <th scope="row">Default</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-primary">
            <th scope="row">Primary</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-secondary">
            <th scope="row">Secondary</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-success">
            <th scope="row">Success</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-danger">
            <th scope="row">Danger</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-warning">
            <th scope="row">Warning</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-info">
            <th scope="row">Info</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-light">
            <th scope="row">Light</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
        <tr class="table-dark">
            <th scope="row">Dark</th>
            <td>Column content</td>
            <td>Column content</td>
            <td>Column content</td>
        </tr>
    </tbody>
</table>
```
----
# Form

##### Label
<div class="row mb-2">
    <div class="col-md-2">
        <label class="control-label control-label-danger">
            <span>Require</span>
        </label>
    </div>
    <div class="col-md-2">
        <label class="control-label">
            <span>Not require</span>
        </label>
    </div>
    <div class="col-md-2">
        <label class="control-label control-label-danger">
            <span>Require</span>
            <span>Constraint</span>
        </label>
    </div>
    <div class="col-md-2">
        <label class="control-label">
            <span>Not require</span>
            <span>Constraint</span>
        </label>
    </div>
    <div class="col-md-2">
        <label class="control-label control-label-inline control-label-danger">
            <span>Require</span>
            <span>Constraint</span>
        </label>
    </div>
    <div class="col-md-2">
        <label class="control-label control-label-inline">
            <span>Not require</span>
            <span>Constraint</span>
        </label>
    </div>
</div>

#### Code
```html
<!-- required label -->
<label class="control-label control-label-danger">
    <span>Require</span>
</label>
<!-- non required label -->
<label class="control-label">
    <span>Not require</span>
</label>
<!-- required label with constraint -->
<label class="control-label control-label-danger">
    <span>Require</span>
    <span>Constraint</span>
</label>
<!-- non required label with constraint -->
<label class="control-label">
    <span>Not require</span>
    <span>Constraint</span>
</label>
<!-- required label with constraint inline -->
<label class="control-label control-label-inline control-label-danger">
    <span>Require</span>
    <span>Constraint</span>
</label>
<!-- non required label with constraint inline -->
<label class="control-label control-label-inline">
    <span>Not require</span>
    <span>Constraint</span>
</label>
```

##### Input text
<div class="row">
    <div class="col-md-6">
        <div class="form-group">
            <div class="control-label control-label-block mb-1 control-label-danger">
                <span>Tile without constraint</span>
            </div>
            <input type="text" class="form-control" />
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="control-label control-label-block mb-1 control-label-danger">
                <span>Tile</span>
                <span>constraint</span>
            </div>
            <input type="text" class="form-control" />
        </div>
    </div>
</div>

##### Code
```html
<!-- without constraint -->
<div class="form-group">
    <div class="control-label control-label-block mb-1 control-label-danger">
        <span>Tile without constraint</span>
    </div>
    <input type="text" class="form-control" />
</div>
<!-- with constraint -->
<div class="form-group">
    <div class="control-label control-label-block mb-1 control-label-danger">
        <span>Tile</span>
        <span>constraint</span>
    </div>
    <input type="text" class="form-control" />
</div>
```

##### Textarea
<div class="form-group">
    <div class="control-label control-label-block mb-1">
        <span>Tile</span>
        <span>constraint</span>
    </div>
    <textarea class="form-control" rows="3"></textarea>
</div>

##### Code
```html
<div class="form-group">
    <div class="control-label control-label-block mb-1">
        <span>Tile</span>
        <span>constraint</span>
    </div>
    <textarea class="form-control" rows="3"></textarea>
</div>
```

##### Select
<div class="row">
    <div class="col-md-6">
        <div class="form-group">
            <div class="control-label control-label-block mb-1 control-label-danger">
                <span>Select control</span>
            </div>
            <select class="form-control">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
            </select>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="control-label control-label-block mb-1 control-label-danger">
                <span>Select control</span>
            </div>
            <select multiple="3" class="form-control">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
            </select>
        </div>
    </div>
</div>

##### Code 
```html
<!-- single select -->
<div class="form-group">
    <div class="control-label control-label-block mb-1 control-label-danger">
        <span>Select control</span>
    </div>
    <select class="form-control">
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
    </select>
</div>
<!-- multi select -->
<div class="form-group">
    <div class="control-label control-label-block mb-1 control-label-danger">
        <span>Select control</span>
    </div>
    <select multiple="3" class="form-control">
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
    </select>
</div>
```