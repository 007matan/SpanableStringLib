# Easy Spannable

### SpanableStringLib is an Android library designed to simplify the usage of 'SpannableString'. It provides an easy-to-use API for applying various spans to text, making it more convenient to style strings without dealing with the complexities of 'SpannableString; directly.

## Features
('*') Apply different spans (e.g., Bold, Underline, Color).  
('*') Chain multiple spans for complex text styling.  
('*') Support custome spans.  

## Usage
### Basic Usage
SpanText.SpanString spanString = new SpanText.SpanString();
spanString.addNewLine("Hello From Cohen");
SpanText spanText = new SpanText();
spanText.add(spanString);

### Applying Color, Size, Shadow
SpanText.SpanString spanString = new SpanText.SpanString();
spanString.addNewLine("Hello From")
        .add(new SpanText.Size(10))
        .add(new SpanText.Color(Color.YELLOW))
        .add(new SpanText.Shadow(2.5F, 0.2F, 0.4F, Color.GREEN));
SpanText spanText = new SpanText();
spanText.add(spanString);

### Applying two Strings With different spans
SpanText.SpanString spanString = new SpanText.SpanString();
spanString.addNewLine("Hello From")
        .add(new SpanText.Size(10))
        .add(new SpanText.Color(Color.YELLOW))
        .add(new SpanText.Shadow(2.5F, 0.2F, 0.4F, Color.GREEN));
SpanText.SpanString spanString2 = new SpanText.SpanString();
spanString2.addNewLine("Matan")
        .add(new SpanText.Size(2))
        .add(new SpanText.Blur(3.5F));
SpanText spanText = new SpanText();
spanText.add(spanString).add(spanString2);

## License
Copyright (c) 2015-2024 Matan Ovadya Cohen Tsedek

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.
