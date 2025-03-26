### Sample Input to the JinjavaParser (LayoutParser)
```
<div>
  {% for user in users %}
    <p>{{ user.name }}</p>
    {% if user.name == "Bob" %}
      <p>Bob Found</p>
      {{{MainSection}}}
    {% else %}
      <p>No Bob</p>
    {% endif %}
  {% endfor %}
</div>
```


### Sample output from the LayoutParser --> String Output
```
<div>
    <p>Alice</p>
    <p>Bob</p>
      <p>Bob Found</p>
        {{{MainSection}}}
      <p>No Bob</p>
</div>
```

### Sample output from the ComponentEngine
```
<div>
    <p>Alice</p>
    <p>Bob</p>
      <p>Bob Found</p>
        <div>The content of Main Section</div>
      <p>No Bob</p>
</div>
```
