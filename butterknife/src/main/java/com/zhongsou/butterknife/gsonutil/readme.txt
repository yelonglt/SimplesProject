调用Gson解析Person
Person bean=GsonImpl.get().toObject("", Person.class);
