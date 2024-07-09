# Easy Spannable

### SpanableStringLib is an Android library designed to simplify the usage of 'SpannableString'. It provides an easy-to-use API for applying various spans to text, making it more convenient to style strings without dealing with the complexities of 'SpannableString; directly.

## Features
('*') Apply different spans (e.g., Bold, Underline, Color).  
('*') Chain multiple spans for complex text styling.  
('*') Support custome spans.  

## Usage
### Basic Usage
'''java 
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

Licensed under the Apache License, Version 2.0 (the "License");  
you may not use this file except in compliance with the License.  
You may obtain a copy of the License at  

   http://www.apache.org/licenses/LICENSE-2.0  

Unless required by applicable law or agreed to in writing, software  
distributed under the License is distributed on an "AS IS" BASIS,  
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
See the License for the specific language governing permissions and  
limitations under the License.  
