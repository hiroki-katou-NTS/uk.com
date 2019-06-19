##### Hiển thị bình thường
<div class="card">
  <h5 class="card-header">Featured</h5>
  <div class="card-body">
    <h5 class="card-title">Special title treatment</h5>
    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
    <a href="javascript:void(0)" class="btn btn-primary">Go somewhere</a>
  </div>
</div>

---
##### Code
```html
<div class="card">
  <h5 class="card-header">Featured</h5>
  <div class="card-body">
    <h5 class="card-title">Special title treatment</h5>
    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
    <a class="btn btn-primary">Go somewhere</a>
  </div>
</div>
```
##### Hiển thị dạng nhóm nhãn
<div class="card card-label mb-2">
  <div class="card-header">
    <span>Featured</span>
    <span class="badge badge-danger">Bắt buộc</span>
  </div>
  <div class="card-body">
    <input type="text" class="form-control" />
  </div>
</div>
<div class="card card-label mb-2">
  <div class="card-header">
    <span>Featured</span>
    <span class="badge badge-danger">Bắt buộc</span>
  </div>
  <div class="card-body">
    <span>Title button 1</span>
    <button type="button" class="btn btn-selection mt-2 mb-2">
        <span class="badge badge-secondary">0001</span>
        <span>Name of selection</span>
    </button>
    <span>Title button 2</span>
    <button type="button" class="btn btn-selection mt-2 mb-2">
        <span class="badge badge-secondary">0001</span>
        <span>Name of selection</span>
        <span class="d-block mt-1">2010~2019</span>
    </button>
  </div>
</div>
<div class="card card-label mb-2">
  <div class="card-header">
    <span>Featured</span>
    <span class="badge badge-info">Tùy chọn</span>
  </div>
  <div class="card-body">
    <input type="text" class="form-control" />
  </div>
</div>

---
##### Code
```html
<div class="card card-label">
    <!-- card header: label -->
  <div class="card-header">
    <span>Featured</span>
    <span class="badge badge-danger">Bắt buộc</span>
  </div>
  <!-- card body: controls -->
  <div class="card-body">
    <input type="text" class="form-control" />
  </div>
</div>
```