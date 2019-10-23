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
##### Checkbox group
<div class="btn-group btn-group-toggle mb-3">
    <label class="btn btn-primary">
        <input type="checkbox" checked autocomplete="off"> Active
    </label>
    <label class="btn btn-primary">
        <input type="checkbox" autocomplete="off"> Check
    </label>
    <label class="btn btn-primary" >
        <input type="checkbox" autocomplete="off"> Check
    </label>
</div>

##### Code
```html
<div class="btn-group btn-group-toggle">
    <label class="btn btn-primary" >
        <input type="checkbox" checked autocomplete="off" /> Active
    </label>
    <label class="btn btn-primary">
        <input type="checkbox" autocomplete="off" /> Check
    </label>
    <label class="btn btn-primary">
        <input type="checkbox" autocomplete="off" /> Check
    </label>
</div>
```

##### Switch button
<div class="btn-group btn-group-toggle mb-3">
    <label class="btn btn-primary">
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
    <label class="btn btn-primary">
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
